package com.fhsh.daitda.delivery.infrastructure.external.adapter;

import com.fhsh.daitda.delivery.application.client.DeliveryManagerClient;
import com.fhsh.daitda.delivery.infrastructure.external.dto.DeliveryManagerAssignRequest;
import com.fhsh.daitda.delivery.infrastructure.external.feignClient.DeliveryManagerFeignClient;
import com.fhsh.daitda.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeliveryManagerAdapter implements DeliveryManagerClient {

    private final DeliveryManagerFeignClient deliveryManagerFeignClient;

    @Override
    public CommonResponse<UUID> assignHubDeliveryManager(UUID companyId) {
        return null;
    }

    @Override
    public CommonResponse<UUID> assignCompanyDeliveryManager(UUID deliveryId, UUID hubId) {
        return deliveryManagerFeignClient.assignCompanyDeliveryManager(new DeliveryManagerAssignRequest(deliveryId, hubId));
    }
}
