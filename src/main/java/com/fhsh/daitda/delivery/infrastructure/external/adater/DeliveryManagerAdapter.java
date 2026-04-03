package com.fhsh.daitda.delivery.infrastructure.external.adater;

import com.fhsh.daitda.delivery.application.client.CompanyClient;
import com.fhsh.daitda.delivery.application.client.DeliveryManagerClient;
import com.fhsh.daitda.delivery.application.client.response.CompanyHubInfo;
import com.fhsh.daitda.delivery.application.client.response.DeliveryManagerInfo;
import com.fhsh.daitda.delivery.infrastructure.external.feignClient.CompanyFeignClient;
import com.fhsh.daitda.delivery.infrastructure.external.feignClient.DeliveryManagerFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeliveryManagerAdapter implements DeliveryManagerClient {

    private final DeliveryManagerFeignClient deliveryManagerFeignClient;

    @Override
    public UUID assignHubDeliveryManager(UUID companyId) {
        return null;
    }

    @Override
    public UUID assignCompanyDeliveryManager(UUID companyId, UUID hubId) {
        return deliveryManagerFeignClient.assignCompanyDeliveryManager(companyId, hubId);
    }
}
