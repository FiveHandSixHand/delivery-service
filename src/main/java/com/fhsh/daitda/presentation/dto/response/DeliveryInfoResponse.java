package com.fhsh.daitda.presentation.dto.response;

import com.fhsh.daitda.application.result.DeliveryResult;
import com.fhsh.daitda.domain.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeliveryInfoResponse {

    private UUID id;
    private UUID orderId;
    private DeliveryStatus status;
    private UUID sourceHubId;
    private UUID destinationHubId;
    private UUID receiverTenantId;
    private UUID senderTenantId;
    private String senderTenantAddress;
    private String receiverTenantAddress;
    private UUID receiverId;
    private UUID senderId;

    public static DeliveryInfoResponse from(DeliveryResult result) {
        return new DeliveryInfoResponse(
                result.getId(),
                result.getOrderId(),
                result.getStatus(),
                result.getDepartureHubId(),
                result.getDestinationHubId(),
                result.getReceiverTenantId(),
                result.getSenderTenantId(),
                result.getSenderTenantAddress(),
                result.getReceiverTenantAddress(),
                result.getReceiverId(),
                result.getSenderId()
        );
    }
}