package com.fhsh.daitda.delivery.infrastructure.external.feignClient;

import com.fhsh.daitda.delivery.infrastructure.external.dto.DeliveryManagerAssignRequest;
import com.fhsh.daitda.delivery.infrastructure.external.dto.SlackNotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "notification-service")
public interface SlackFeignClient {

    @PostMapping("/internal/v1/slackmessages")
    UUID sendSlackMessage(@RequestBody SlackNotificationRequest request);
}
