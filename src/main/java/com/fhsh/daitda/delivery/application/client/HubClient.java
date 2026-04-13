package com.fhsh.daitda.delivery.application.client;

import com.fhsh.daitda.delivery.application.client.response.HubRouteInfoResponse;

import java.util.List;
import java.util.UUID;

public interface HubClient {
    UUID getHubIdByManagerId(UUID userId);
    HubRouteInfoResponse getHubRouteInfo(UUID supplierCompanyId, UUID receiverCompanyId);
    List<HubRouteInfoResponse> getHubRoutePath(UUID srcHubId, UUID destHubId);
}
