package com.fhsh.daitda.delivery.infrastructure.external.adapter;

import com.fhsh.daitda.delivery.application.client.HubClient;
import com.fhsh.daitda.delivery.application.client.response.HubRouteInfoResponse;
import com.fhsh.daitda.delivery.domain.vo.HubRouteInfo;
import com.fhsh.daitda.delivery.infrastructure.external.feignClient.HubFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HubAdapter implements HubClient {
    private final HubFeignClient hubFeignClient;

    @Override
    public UUID getHubIdByManagerId(UUID userId) {
        return hubFeignClient.getHubIdByManagerId(userId);
    }

    @Override
    public HubRouteInfoResponse getHubRouteInfo(UUID supplierCompanyId, UUID receiverCompanyId) {
        return hubFeignClient.getHubRouteInfo(supplierCompanyId, receiverCompanyId);
    }

    @Override
    public List<HubRouteInfoResponse> getHubRoutePath(UUID srcHubId, UUID destHubId) {
        return hubFeignClient.getHubRoutePath(srcHubId, destHubId);
    }
}
