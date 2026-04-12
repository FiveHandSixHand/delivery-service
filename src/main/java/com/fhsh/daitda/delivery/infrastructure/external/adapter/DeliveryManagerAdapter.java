package com.fhsh.daitda.delivery.infrastructure.external.adapter;

import com.fhsh.daitda.delivery.application.client.DeliveryManagerClient;
import com.fhsh.daitda.delivery.infrastructure.external.dto.DeliveryManagerAssignRequest;
import com.fhsh.daitda.delivery.infrastructure.external.feignClient.DeliveryManagerFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeliveryManagerAdapter implements DeliveryManagerClient {

    private final DeliveryManagerFeignClient deliveryManagerFeignClient;

    @Override
    public UUID assignHubDeliveryManager(UUID deliveryId) {
        return deliveryManagerFeignClient.assignDeliveryManager(
                new DeliveryManagerAssignRequest(deliveryId, null)).getData().getDeliveryManagerId();
    }

    @Override
    public UUID assignCompanyDeliveryManager(UUID deliveryId, UUID hubId) {
        return deliveryManagerFeignClient.assignDeliveryManager(
                new DeliveryManagerAssignRequest(deliveryId, hubId)).getData().getDeliveryManagerId();
    }

    @Override
    public void cancelHubManagers(List<UUID> hubManagerIds) {
        deliveryManagerFeignClient.cancelHubManagers(hubManagerIds);
    }
}
