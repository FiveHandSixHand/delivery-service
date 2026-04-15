package com.fhsh.daitda.delivery.application.service.command;

import brave.internal.Nullable;
import com.fhsh.daitda.delivery.application.client.CompanyClient;
import com.fhsh.daitda.delivery.application.client.DeliveryManagerClient;
import com.fhsh.daitda.delivery.application.client.HubClient;
import com.fhsh.daitda.delivery.application.client.response.CompanyHubInfoResponse;
import com.fhsh.daitda.delivery.application.client.response.HubRouteInfoResponse;
import com.fhsh.daitda.delivery.application.command.DeliveryCreateCommand;
import com.fhsh.daitda.delivery.application.result.DeliveryCreateResult;
import com.fhsh.daitda.delivery.application.result.DeliveryStatusUpdateResult;
import com.fhsh.daitda.delivery.application.state.DeliveryStateFactory;
import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.entity.Outbox;
import com.fhsh.daitda.delivery.domain.entity.DeliveryRoute;
import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import com.fhsh.daitda.delivery.domain.exception.DeliveryErrorCode;
import com.fhsh.daitda.delivery.domain.repository.DeliveryRepository;
import com.fhsh.daitda.delivery.domain.state.DeliveryState;
import com.fhsh.daitda.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryCommandService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryProcessor deliveryProcessor;
    private final DeliveryStateFactory deliveryStateFactory;
    private final DeliveryManagerClient deliveryManagerClient;
    private final CompanyClient companyClient;
    private final HubClient hubClient;

    public DeliveryCreateResult registerDelivery(DeliveryCreateCommand command) {

        // 공급업체와 수령업체 허브 정보 조회
        CompanyHubInfoResponse supplierHubResponse = companyClient.getHubIdByManagerId(command.getSupplierCompanyId());
        CompanyHubInfoResponse receiverHubResponse = companyClient.getHubIdByManagerId(command.getReceiverCompanyId());
        List<HubRouteInfoResponse> hubRouteInfoResponseList = hubClient.getHubRoutePath(supplierHubResponse.getHubId(), receiverHubResponse.getHubId());

        Delivery delivery = null;
        UUID managerId;
        UUID deliveryId = UUID.randomUUID();
        List<UUID> hubManagerIds = new ArrayList<>();

        for (HubRouteInfoResponse route : hubRouteInfoResponseList) {
            hubManagerIds.add(deliveryManagerClient.assignHubDeliveryManager(deliveryId));
        }

        // 담당자 배정
        try {
            delivery = deliveryProcessor.createAndSaveWithOutbox(
                    deliveryId,
                    command,
                    supplierHubResponse,
                    receiverHubResponse,
                    hubRouteInfoResponseList,
                    hubManagerIds
            );

            deliveryProcessor.assignHubManagers(delivery, hubManagerIds);
            managerId = deliveryManagerClient.assignCompanyDeliveryManager(delivery.getId(), receiverHubResponse.getHubId());

        } catch (Exception e) {
            compensate(hubManagerIds, delivery);
            throw new BusinessException(DeliveryErrorCode.DELIVERY_CREATION_FAILED);
        }
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

    public void deleteDelivery(UUID deliveryId) {
        deliveryProcessor.deleteDelivery(deliveryId);
    }

    // Helper Method
    private Delivery getDelivery(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY));
    }

    private void compensate(List<UUID> hubManagerIds, @Nullable Delivery delivery) {
        try {
            if (!hubManagerIds.isEmpty()) {
                deliveryManagerClient.cancelHubManagers(hubManagerIds);
            }
            if (delivery != null) {
                deliveryProcessor.deleteDelivery(delivery.getId());
            }
        } catch (Exception e) {
            log.error("보상 트랜잭션 실패. 스케줄러 재처리 예정. hubManagerIds: {}", hubManagerIds);
        }
    }
}
