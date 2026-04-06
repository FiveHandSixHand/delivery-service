package com.fhsh.daitda.delivery.infrastructure.external.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class SlackNotificationRequest {
    private String receiverSlackId;
    private String message;
    private UUID orderId;
    private String messageType;
}