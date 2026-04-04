package com.fhsh.daitda.delivery.infrastructure.external.adapter;

import com.fhsh.daitda.delivery.application.client.SlackClient;
import com.fhsh.daitda.delivery.infrastructure.external.feignClient.SlackFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SlackAdapter implements SlackClient {

    private final SlackFeignClient slackFeignClient;

    @Override
    public void sendMessage(String receiverEmail, String format, UUID orderId, String hubTransitStart) {
    }
}
