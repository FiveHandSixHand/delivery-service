package com.fhsh.daitda.delivery.application.client;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface HubClient {
    Map<UUID, String> getHubIdByManagerId(UUID userId);
}
