package com.fhsh.daitda.delivery.presentation.dto.response;

import com.fhsh.daitda.delivery.application.result.DeliveryCreateResult;
import com.fhsh.daitda.delivery.application.result.DeliveryResult;
import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeliveryInfoResponse {

    private UUID deliveryId;
    private UUID orderId;
    private DeliveryStatus status;
    private UUID srcHubId;
    private UUID destHubId;
    private String senderTenantAddress;
    private String receiverTenantAddress;
    private UUID receiverId;
    private UUID deliveryManagersId;

    public static DeliveryInfoResponse from(DeliveryResult result) {
        return new DeliveryInfoResponse(
                result.getId(),
                result.getOrderId(),
                result.getStatus(),
                result.getDepartureHubId(),
                result.getDestinationHubId(),
                result.getSenderTenantAddress(),
                result.getReceiverTenantAddress(),
                result.getReceiverId(),
                result.getDeliveryManagerId()
        );
    }
}