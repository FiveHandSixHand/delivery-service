package com.fhsh.daitda.delivery.domain.repository;

import com.fhsh.daitda.delivery.domain.entity.Outbox;
import com.fhsh.daitda.delivery.domain.enums.DeliveryOutBoxStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryOutboxRepository {
    List<Outbox> findByStatus(DeliveryOutBoxStatus status);
    Outbox save(Outbox outbox);
    Optional<Outbox> getDeliveryOutbox(UUID outboxId);
}
