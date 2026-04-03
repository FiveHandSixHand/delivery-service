package com.fhsh.daitda.delivery.application.service.command;

import com.fhsh.daitda.delivery.application.client.CompanyClient;
import com.fhsh.daitda.delivery.application.client.DeliveryManagerClient;
import com.fhsh.daitda.delivery.application.client.HubClient;
import com.fhsh.daitda.delivery.application.client.response.CompanyHubInfo;
import com.fhsh.daitda.delivery.application.command.DeliveryCreateCommand;
import com.fhsh.daitda.delivery.application.result.DeliveryCreateResult;
import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryCommandService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryManagerClient deliveryManagerClient;
    private final CompanyClient companyClient;
    private final HubClient hubClient;

    public DeliveryCreateResult registerDelivery(DeliveryCreateCommand command) {
        // 1. 공급업체와 수령업체 허브 정보 조회
        CompanyHubInfo supplierHub = companyClient.getHubIdByManagerId(command.getSupplierCompanyId());
        CompanyHubInfo receiverHub = companyClient.getHubIdByManagerId(command.getReceiverCompanyId());

        // 2. 배송 객체 생성 및 저장
        Delivery delivery = Delivery.create(command, supplierHub, receiverHub);
        deliveryRepository.save(delivery);

        // 3. 배송담당자 배정
        UUID deliveryId = delivery.getId();
        UUID companyDeliveryManagerId = deliveryManagerClient.assignCompanyDeliveryManager(deliveryId, receiverHub.getHubId());

        // 4. 배송담당자 정보 업데이트
        delivery.assignManagers(companyDeliveryManagerId);

        return DeliveryCreateResult.from(delivery);
    }
}
