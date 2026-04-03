package com.fhsh.daitda.delivery.infrastructure.external.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "delivery-service")
public interface DeliveryManagerFeignClient {

    @GetMapping("/internal/v1/companies/{companyId}")
    UUID assignCompanyDeliveryManager(UUID companyId, @PathVariable("deliveryId") UUID deliveryId);
}
