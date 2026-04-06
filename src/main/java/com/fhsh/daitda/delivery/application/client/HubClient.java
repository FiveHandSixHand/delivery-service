package com.fhsh.daitda.delivery.application.client;

import com.fhsh.daitda.delivery.application.client.response.HubRouteInfoResponse;
import com.fhsh.daitda.delivery.domain.vo.HubRouteInfo;

import java.util.UUID;

public interface HubClient {
    UUID getHubIdByManagerId(UUID userId);
    HubRouteInfoResponse getHubRouteInfo(UUID supplierCompanyId, UUID receiverCompanyId);
}
