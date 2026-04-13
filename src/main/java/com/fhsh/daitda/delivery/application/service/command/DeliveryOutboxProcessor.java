package com.fhsh.daitda.delivery.application.service.command;

import com.fhsh.daitda.delivery.domain.entity.DeliveryOutbox;
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
    public DeliveryOutbox save(List<UUID> hubManagerIds)
    {
        return deliveryOutboxRepository.save(DeliveryOutbox.create(hubManagerIds));
    }

    @Transactional
    public void complete(UUID outboxId) {
        DeliveryOutbox deliveryOutbox = deliveryOutboxRepository.getDeliveryOutbox(outboxId)
                .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY_OUTBOX));
        deliveryOutbox.complete();
    }

    @Transactional(readOnly = true)
    public List<DeliveryOutbox> getDeliveryOutboxes() {
        return deliveryOutboxRepository.findByStatus(DeliveryOutBoxStatus.PENDING);
    }
}
