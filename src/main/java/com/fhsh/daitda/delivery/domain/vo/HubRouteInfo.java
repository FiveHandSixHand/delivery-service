package com.fhsh.daitda.delivery.domain.vo;

import com.fhsh.daitda.delivery.application.client.response.HubRouteInfoResponse;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HubRouteInfo {
    private UUID srcHubId;
    private UUID destHubId;
    private int duration;
    private double distance;
}