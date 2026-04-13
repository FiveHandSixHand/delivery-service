package com.fhsh.daitda.delivery.application.client;

import java.util.List;
import java.util.UUID;

public interface DeliveryManagerClient {
    UUID assignHubDeliveryManager(UUID deliveryId);
    UUID assignCompanyDeliveryManager(UUID deliveryId, UUID hubId);
    void cancelHubManagers(List<UUID> hubManagerIds);
}
