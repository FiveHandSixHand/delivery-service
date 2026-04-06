package com.fhsh.daitda.delivery.infrastructure.external.adapter;

import com.fhsh.daitda.delivery.application.client.CompanyClient;
import com.fhsh.daitda.delivery.application.client.response.CompanyHubInfoResponse;
import com.fhsh.daitda.delivery.infrastructure.external.feignClient.CompanyFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CompanyAdapter implements CompanyClient {
    private final CompanyFeignClient companyFeignClient;

    @Override
    public CompanyHubInfoResponse getHubIdByManagerId(UUID companyId) {
        return companyFeignClient.getHubIdByManagerId(companyId).getData();
    }
}
