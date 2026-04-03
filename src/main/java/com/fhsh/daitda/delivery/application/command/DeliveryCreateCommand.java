package com.fhsh.daitda.delivery.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryCreateCommand {
    private UUID orderId;
    private UUID supplierCompanyId;
    private UUID receiverCompanyId;
}
