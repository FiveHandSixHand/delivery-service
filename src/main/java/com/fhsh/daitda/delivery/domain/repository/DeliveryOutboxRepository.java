package com.fhsh.daitda.delivery.domain.repository;

import com.fhsh.daitda.delivery.domain.entity.DeliveryOutbox;
import com.fhsh.daitda.delivery.domain.enums.DeliveryOutBoxStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryOutboxRepository {
    List<DeliveryOutbox> findByStatus(DeliveryOutBoxStatus status);
    DeliveryOutbox save(DeliveryOutbox outbox);
    Optional<DeliveryOutbox> getDeliveryOutbox(UUID outboxId);
}
