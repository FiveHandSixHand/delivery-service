package com.fhsh.daitda.delivery.application.service.command;

import com.fhsh.daitda.delivery.domain.entity.Outbox;
import com.fhsh.daitda.delivery.domain.enums.DeliveryOutBoxStatus;
import com.fhsh.daitda.delivery.domain.exception.DeliveryErrorCode;
import com.fhsh.daitda.delivery.domain.repository.DeliveryOutboxRepository;
import com.fhsh.daitda.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeliveryOutboxProcessor {

    private final DeliveryOutboxRepository deliveryOutboxRepository;

    @Transactional
    public Outbox save(List<UUID> hubManagerIds, String topic)
    {
        return deliveryOutboxRepository.save(Outbox.create(hubManagerIds, topic));
    }

    @Transactional
    public void complete(UUID outboxId) {
        Outbox deliveryOutbox = deliveryOutboxRepository.getDeliveryOutbox(outboxId)
                .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY_OUTBOX));
        deliveryOutbox.complete();
    }

    @Transactional
    public void fail(UUID outboxId) {
        Outbox deliveryOutbox = deliveryOutboxRepository.getDeliveryOutbox(outboxId)
                .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY_OUTBOX));
        deliveryOutbox.fail();
    }

    @Transactional(readOnly = true)
    public List<Outbox> getDeliveryOutboxes() {
        return deliveryOutboxRepository.findByStatus(DeliveryOutBoxStatus.PENDING);
    }

}
