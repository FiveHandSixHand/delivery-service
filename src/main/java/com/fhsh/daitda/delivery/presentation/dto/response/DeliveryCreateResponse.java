package com.fhsh.daitda.delivery.presentation.dto.response;

import com.fhsh.daitda.delivery.application.result.DeliveryCreateResult;
import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeliveryCreateResponse {

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

    public static DeliveryCreateResponse from(DeliveryCreateResult result) {
        return new DeliveryCreateResponse(
                result.getDeliveryId(),
                result.getOrderId(),
                result.getStatus(),
                result.getSrcHubId(),
                result.getDestHubId(),
                result.getSenderTenantAddress(),
                result.getReceiverTenantAddress(),
                result.getReceiverId(),
                result.getDeliveryManagerId(),
                result.getCreatedAt()
        );
    }
}
