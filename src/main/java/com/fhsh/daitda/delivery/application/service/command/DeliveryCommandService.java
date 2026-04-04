package com.fhsh.daitda.delivery.application.service.command;

import com.fhsh.daitda.delivery.application.client.CompanyClient;
import com.fhsh.daitda.delivery.application.client.DeliveryManagerClient;
import com.fhsh.daitda.delivery.application.client.response.CompanyHubInfo;
import com.fhsh.daitda.delivery.application.command.DeliveryCreateCommand;
import com.fhsh.daitda.delivery.application.result.DeliveryStatusUpdateResult;
import com.fhsh.daitda.delivery.application.result.DeliveryCreateResult;
import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import com.fhsh.daitda.delivery.domain.exception.DeliveryErrorCode;
import com.fhsh.daitda.delivery.domain.repository.DeliveryRepository;
import com.fhsh.daitda.delivery.domain.state.DeliveryState;
import com.fhsh.daitda.delivery.application.state.DeliveryStateFactory;
import com.fhsh.daitda.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryCommandService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryStateFactory deliveryStateFactory;
    private final DeliveryManagerClient deliveryManagerClient;
    private final CompanyClient companyClient;
    private final DeliveryProcessor deliveryProcessor;

    public DeliveryCreateResult registerDelivery(DeliveryCreateCommand command) {

        // 공급업체와 수령업체 허브 정보 조회
        // TODO 외부 서비스 호출 실패 시 보상 로직 필요
        CompanyHubInfo supplierHub = companyClient.getHubIdByManagerId(command.getSupplierCompanyId());
        CompanyHubInfo receiverHub = companyClient.getHubIdByManagerId(command.getReceiverCompanyId());

        // DB 작업
        Delivery delivery = deliveryProcessor.createAndSave(command, supplierHub, receiverHub);

        // 담당자 배정
        UUID managerId = deliveryManagerClient.assignCompanyDeliveryManager(delivery.getId(), receiverHub.getHubId());

        // 담당자 업데이트
        return deliveryProcessor.assignManager(delivery, managerId);
    }

    @Transactional
    public DeliveryStatusUpdateResult updateStatus(UUID deliveryId, DeliveryStatus status, String email) {
        Delivery delivery = getDelivery(deliveryId);
        DeliveryState state = deliveryStateFactory.getState(delivery.getStatus());
        status.execute(state, delivery, email);

        return DeliveryStatusUpdateResult.from(delivery);
    }

    @Transactional
    public void cancelDelivery(UUID deliveryId) {
        Delivery delivery = getDelivery(deliveryId);
        delivery.cancel();
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
