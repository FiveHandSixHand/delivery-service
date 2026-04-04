package com.fhsh.daitda.delivery.application.service.query;

import com.fhsh.daitda.delivery.application.client.HubClient;
import com.fhsh.daitda.delivery.application.result.DeliveryResult;
import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.exception.DeliveryErrorCode;
import com.fhsh.daitda.delivery.domain.repository.DeliveryRepository;
import com.fhsh.daitda.exception.BusinessException;
import com.fhsh.daitda.exception.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryQueryService {

    private final DeliveryRepository deliveryRepository;
    private final HubClient hubClient;

    public DeliveryResult findDelivery(UUID deliveryId) {

        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY));

        return DeliveryResult.from(delivery);
    }

    //TODO HUB API 요청
    public Slice<DeliveryResult> getDeliveries(UUID userId, String role, Pageable pageable) {

        Slice<Delivery> deliveries = switch (role) {
            case "MASTER", "COMPANY_MANAGER" -> deliveryRepository.findAll(pageable);
            case "HUB_MANAGER" -> getDeliveriesForHubManager(userId, pageable);
            case "DELIVERY_MANAGER" -> deliveryRepository.findByDeliveryManagerId(userId, pageable);
            default -> throw new BusinessException(CommonErrorCode.FORBIDDEN);
        };
        return deliveries.map(DeliveryResult::from);
    }

    // Helper Method
    private Slice<Delivery> getDeliveriesForHubManager(UUID userId, Pageable pageable) {
        UUID hubId = hubClient.getHubIdByManagerId(userId);
        return deliveryRepository.findByHubId(hubId, pageable);
    }
}
