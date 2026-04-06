package com.fhsh.daitda.delivery.application.result;

import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeliveryCreateResult {

    private UUID deliveryId;
    private UUID orderId;
    private DeliveryStatus status;
    private UUID srcHubId;
    private UUID destHubId;
    private String senderTenantAddress;
    private String receiverTenantAddress;
    private UUID receiverId;
    private UUID deliveryManagerId;
    private LocalDateTime createdAt;

    public static DeliveryCreateResult from(Delivery delivery) {
        return new DeliveryCreateResult(
                delivery.getId(),
                delivery.getOrderId(),
                delivery.getStatus(),
                delivery.getDepartureHubId(),
                delivery.getDestinationHubId(),
                delivery.getSenderTenantAddress(),
                delivery.getReceiverTenantAddress(),
                delivery.getReceiverId(),
                delivery.getDeliveryManagerId(),
                delivery.getCreatedAt()
        );
    }
}
