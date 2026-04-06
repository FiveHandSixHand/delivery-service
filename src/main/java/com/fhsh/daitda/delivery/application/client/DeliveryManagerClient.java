package com.fhsh.daitda.delivery.application.client;

import java.util.UUID;

public interface DeliveryManagerClient {
    UUID assignHubDeliveryManager(UUID deliveryId);
    UUID assignCompanyDeliveryManager(UUID deliveryId, UUID hubId);
}
