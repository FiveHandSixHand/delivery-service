package com.fhsh.daitda.delivery.application.service.command;

import com.fhsh.daitda.delivery.application.client.DeliveryManagerClient;
import com.fhsh.daitda.delivery.domain.entity.DeliveryOutbox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryOutboxScheduler {

    private final DeliveryOutboxProcessor deliveryOutboxProcessor;
    private final DeliveryManagerClient deliveryManagerClient;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void retryPendingOutbox() {
        List<DeliveryOutbox> outboxList = deliveryOutboxProcessor.getDeliveryOutboxes(); //CQRS관점에서 나눠야하는건아닌지?
        for (DeliveryOutbox outbox : outboxList) {
            try{
                deliveryManagerClient.cancelHubManagers(outbox.getHubManagers());
                outbox.complete();
            } catch (RuntimeException e) {
                outbox.increaseRetryCount();
                if (outbox.isExhausted()) {
                    outbox.fail();
                    log.error("해당 배송에 대한 수동 재처리가 필요합니다. DeliveryOutbox ID: {}", outbox.getId());
                }
            }
        }
    }
}
