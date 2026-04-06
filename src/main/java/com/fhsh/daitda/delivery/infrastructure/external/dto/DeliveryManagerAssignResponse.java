package com.fhsh.daitda.delivery.infrastructure.external.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeliveryManagerAssignResponse {
    UUID deliveryManagerId;
}
