package com.fhsh.daitda.delivery.application.client;

import java.util.UUID;

public interface DeliveryManagerClient {
    UUID assignHubDeliveryManager(UUID companyId);
    UUID assignCompanyDeliveryManager(UUID deliveryId, UUID hubId);
}
