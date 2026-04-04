package com.fhsh.daitda.delivery.application.re;

import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeliveryStatusUpdateResult {
    private UUID deliveryId;
    private DeliveryStatus status;
    private LocalDateTime updatedAt;

    public static DeliveryStatusUpdateResult from(Delivery delivery) {
        return new DeliveryStatusUpdateResult(
                delivery.getId(),
                delivery.getStatus(),
                delivery.getUpdatedAt()
        );
    }
}
