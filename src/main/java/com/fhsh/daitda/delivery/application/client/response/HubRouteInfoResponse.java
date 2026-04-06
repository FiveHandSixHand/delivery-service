package com.fhsh.daitda.delivery.application.client.response;

import com.fhsh.daitda.delivery.domain.vo.HubRouteInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HubRouteInfoResponse {
    private UUID srcHubId;
    private UUID destHubId;
    private int duration;
    private double distance;

    public HubRouteInfo toHubRouteInfo() {
        return new HubRouteInfo(
                this.srcHubId,
                this.destHubId,
                this.duration,
                this.distance
        );
    }
}
