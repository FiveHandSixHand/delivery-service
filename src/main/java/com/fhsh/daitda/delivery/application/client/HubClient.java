package com.fhsh.daitda.delivery.application.client;

import java.util.UUID;

public interface HubClient {
    UUID getHubIdByManagerId(UUID userId);
}
