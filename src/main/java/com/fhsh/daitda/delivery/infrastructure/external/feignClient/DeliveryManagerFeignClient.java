package com.fhsh.daitda.delivery.infrastructure.external.feignClient;

import com.fhsh.daitda.delivery.infrastructure.external.dto.DeliveryManagerAssignRequest;
import com.fhsh.daitda.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "delivery-service")
public interface DeliveryManagerFeignClient {

    @PostMapping("/internal/v1/delivery-managers/assignments")
    CommonResponse<UUID> assignCompanyDeliveryManager(@RequestBody DeliveryManagerAssignRequest request);
}