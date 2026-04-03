package com.fhsh.daitda.delivery.presentation.dto.request;

import com.fhsh.daitda.delivery.application.command.DeliveryCreateCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class DeliveryCreateRequest {
    private UUID orderId;
    private UUID supplierCompanyId;
    private UUID receiverCompanyId;

    public DeliveryCreateCommand toCommand() {
        return new DeliveryCreateCommand(orderId, supplierCompanyId, receiverCompanyId);
    }
}