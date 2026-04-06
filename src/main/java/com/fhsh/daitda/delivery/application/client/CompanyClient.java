package com.fhsh.daitda.delivery.application.client;

import com.fhsh.daitda.delivery.application.client.response.CompanyHubInfoResponse;

import java.util.UUID;

public interface CompanyClient {
    CompanyHubInfoResponse getHubIdByManagerId(UUID companyId);
}
