package com.fhsh.daitda.delivery.infrastructure.external.adapter;

import com.fhsh.daitda.delivery.application.client.DeliveryManagerClient;
import com.fhsh.daitda.delivery.infrastructure.external.dto.DeliveryManagerAssignRequest;
import com.fhsh.daitda.delivery.infrastructure.external.feignClient.DeliveryManagerFeignClient;
import com.fhsh.daitda.exception.BusinessException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
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
    @Retryable(
            retryFor = {FeignException.class},
            maxAttempts = 3,
            backoff = @Backoff(
                    delay = 1000,
                    multiplier = 2
            )
    )
    public void cancelHubManagers(List<UUID> hubManagerIds) {
        deliveryManagerFeignClient.cancelHubManagers(hubManagerIds);
    }

    /*
    * 재시도 횟수를 모두 소진했을 때 실행됩니다.
    * */
    @Recover
    public void recoverCancelHubManagers(FeignException e, List<UUID> hubManagerIds) {
        log.error("[재시도 횟수 소진] 수동 처리 필요 - 취소 필요한 hubManagerIds: {}", hubManagerIds);
        throw new RuntimeException("보상 트랜잭션 최종 실패", e);
    }
}
