package com.fhsh.daitda.delivery.application.service.command;

import com.fhsh.daitda.delivery.application.client.response.CompanyHubInfoResponse;
import com.fhsh.daitda.delivery.application.client.response.HubRouteInfoResponse;
import com.fhsh.daitda.delivery.application.command.DeliveryCreateCommand;
import com.fhsh.daitda.delivery.application.result.DeliveryCreateResult;
import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.entity.DeliveryRoute;
import com.fhsh.daitda.delivery.domain.entity.Outbox;
import com.fhsh.daitda.delivery.domain.exception.DeliveryErrorCode;
import com.fhsh.daitda.delivery.domain.repository.DeliveryRepository;
import com.fhsh.daitda.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeliveryProcessor {

    /**
     * 해당 클래스는 Delivery에 해당하는 DB로직만 처리합니다.
     * <p>
     * [분리 이유]
     * 1. Spring AOP 프록시 특성상 같은 클래스 내부 호출 시 @Transactional이 동작하지 않아
     * 트랜잭션 경계를 명확히 하기 위해 별도 클래스로 분리했습니다.
     * 2. 외부 통신 중 DB 커넥션을 점유하는 문제와 외부 서비스와의 의존성 문제를 해결합니다.
     * <p>
     * [개선 예정]
     * 추후 Kafka 등 메시지 기반 비동기 통신으로 전환하여
     * 외부 서비스 장애가 내 서비스에 전파되지 않도록 리팩토링 예정입니다.
     */

    private final DeliveryRepository deliveryRepository;
    private final DeliveryOutboxProcessor deliveryOutboxProcessor;

    @Transactional
    public Delivery createAndSaveWithOutbox(UUID deliveryId,
                                            DeliveryCreateCommand command,
                                            CompanyHubInfoResponse supplierHubResponse,
                                            CompanyHubInfoResponse receiverHubResponse,
                                            List<HubRouteInfoResponse> hubRouteInfoResponseList,
                                            List<UUID> hubManagerIds) {

        //TO-BE 외부서비스에서 API스팩이 바뀌면 delivery domain까지 문제가 생김 -> application layer dto를 넘겨서는 안되는 이유
        //Delivery delivery = Delivery.create(command, supplierHubResponse, receiverHubResponse);
        Delivery delivery = Delivery.create(
                deliveryId,
                command.getOrderId(),
                supplierHubResponse.getHubId(),
                receiverHubResponse.getHubId(),
                command.getSupplierCompanyId(),
                command.getReceiverCompanyId(),
                supplierHubResponse.getFullAddress(),
                receiverHubResponse.getFullAddress(),
                receiverHubResponse.getHubId(),
                supplierHubResponse.getHubId());



        List<DeliveryRoute> routes = new ArrayList<>();
        for (int i = 0; i < hubRouteInfoResponseList.size(); i++) {
            DeliveryRoute route = DeliveryRoute.create(
                    delivery,
                    hubRouteInfoResponseList.get(i).toHubRouteInfo(),
                    i + 1);
            route.assignManager(hubManagerIds.get(i));
            routes.add(route);
        }

        delivery.addRoutes(routes);
        deliveryOutboxProcessor.save(hubManagerIds, "delivery-manager-service");

        return deliveryRepository.save(delivery);
    }

    @Transactional
    public DeliveryCreateResult assignManager(Delivery delivery, UUID managerId) {
        delivery.assignManagers(managerId);
        deliveryRepository.save(delivery);
        return DeliveryCreateResult.from(delivery);
    }

    @Transactional
    public void assignHubManagers(Delivery delivery, List<UUID> hubManagerIds) {
        List<DeliveryRoute> routes = delivery.getDeliveryRoutes();
        for (int i = 0; i < routes.size(); i++) {
            routes.get(i).assignManager(hubManagerIds.get(i));
        }
        deliveryRepository.save(delivery); // cascade MERGE로 routes도 같이 저장
    }

    @Transactional
    public void deleteDelivery(UUID deliveryId) {
        Delivery delivery = getDelivery(deliveryId);
        delivery.softDelete();
    }

    // Helper Method
    private Delivery getDelivery(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY));
    }
}
