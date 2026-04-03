package com.fhsh.daitda.delivery.application.client.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyHubInfo {
    private UUID hubId;
    private String address;
    private LocalDateTime receivedAt;
    private String receiverName;
}
