package com.fhsh.daitda.delivery.application.result;

import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeliveryResult {

    private UUID id;
    private UUID orderId;
    private DeliveryStatus status;
    private UUID departureHubId;
    private UUID destinationHubId;
    private UUID receiverTenantId;
    private UUID senderTenantId;
    private String senderTenantAddress;
    private String receiverTenantAddress;
    private UUID receiverId;
    private UUID senderId;
    private UUID deliveryManagerId;

    public static DeliveryResult from(Delivery delivery) {
        return new DeliveryResult(
                delivery.getId(),
                delivery.getOrderId(),
                delivery.getStatus(),
                delivery.getDepartureHubId(),
                delivery.getDestinationHubId(),
                delivery.getReceiverTenantId(),
                delivery.getSenderTenantId(),
                delivery.getSenderTenantAddress(),
                delivery.getReceiverTenantAddress(),
                delivery.getReceiverId(),
                delivery.getSenderId(),
                delivery.getDeliveryManagerId()
        );
    }
}