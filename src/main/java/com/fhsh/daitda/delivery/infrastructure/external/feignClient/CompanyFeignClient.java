package com.fhsh.daitda.delivery.infrastructure.external.feignClient;

import com.fhsh.daitda.delivery.application.client.response.CompanyHubInfoResponse;
import com.fhsh.daitda.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "company-service")
public interface CompanyFeignClient {

    @GetMapping("/api/v1/companies/{companyId}")
    CommonResponse<CompanyHubInfoResponse> getHubIdByManagerId(@PathVariable("companyId") UUID companyId);
}
