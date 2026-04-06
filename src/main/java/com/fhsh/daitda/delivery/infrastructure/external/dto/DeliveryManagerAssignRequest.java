package com.fhsh.daitda.delivery.infrastructure.external.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryManagerAssignRequest {
    UUID deliveryId;
    UUID hubId;
}
