package com.fhsh.daitda.delivery.infrastructure.external.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubFeignClient {

    @GetMapping("/internal/v1/hubs/{userId}")
    UUID getHubIdByManagerId(@PathVariable("userId") UUID userId);


}
