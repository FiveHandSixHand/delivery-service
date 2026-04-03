package com.fhsh.daitda.delivery.application.client;

import com.fhsh.daitda.delivery.application.client.response.DeliveryManagerInfo;

import java.util.UUID;

public interface DeliveryManagerClient {
    UUID assignHubDeliveryManager(UUID companyId);
    UUID assignCompanyDeliveryManager(UUID companyId, UUID hubId);
}
