package com.fhsh.daitda.delivery.application.client;

import com.fhsh.daitda.delivery.application.client.response.CompanyHubInfo;

import java.util.UUID;

public interface CompanyClient {
    CompanyHubInfo getHubIdByManagerId(UUID companyId);
}
