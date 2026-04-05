package com.fhsh.daitda.delivery.infrastructure.external.feignClient;

import com.fhsh.daitda.delivery.application.client.response.HubRouteInfoResponse;
import com.fhsh.daitda.delivery.domain.vo.HubRouteInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubFeignClient {

    @GetMapping("/internal/v1/hubs/{userId}")
    UUID getHubIdByManagerId(@PathVariable("userId") UUID userId);

    @GetMapping("/internal/v1/hubs")
    HubRouteInfoResponse getHubRouteInfo(@RequestParam UUID supplierCompanyId, @RequestParam UUID receiverCompanyId);
}
