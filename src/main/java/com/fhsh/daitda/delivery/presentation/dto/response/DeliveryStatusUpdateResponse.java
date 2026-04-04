package com.fhsh.daitda.delivery.presentation.dto.response;

import com.fhsh.daitda.delivery.application.re.DeliveryStatusUpdateResult;
import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeliveryStatusUpdateResponse {
    private UUID deliveryId;
    private DeliveryStatus status;
    private LocalDateTime updatedAt;

    public static DeliveryStatusUpdateResponse from(DeliveryStatusUpdateResult result) {
        return new DeliveryStatusUpdateResponse(
                result.getDeliveryId(),
                result.getStatus(),
                result.getUpdatedAt()
        );
    }
}