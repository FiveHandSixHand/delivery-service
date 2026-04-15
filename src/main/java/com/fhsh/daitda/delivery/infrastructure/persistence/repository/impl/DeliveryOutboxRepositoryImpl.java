package com.fhsh.daitda.delivery.infrastructure.persistence.repository.impl;

import com.fhsh.daitda.delivery.domain.entity.Outbox;
import com.fhsh.daitda.delivery.domain.enums.DeliveryOutBoxStatus;
import com.fhsh.daitda.delivery.domain.repository.DeliveryOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeliveryOutboxRepositoryImpl implements DeliveryOutboxRepository{

    private final DeliveryOutboxJpaRepository deliveryOutboxJpaRepository;

    @Override
    public List<Outbox> findByStatus(DeliveryOutBoxStatus status) {
        return deliveryOutboxJpaRepository.findByStatus(status);
    }

    @Override
    public Outbox save(Outbox outbox) {
        return deliveryOutboxJpaRepository.save(outbox);
    }

    @Override
    public Optional<Outbox> getDeliveryOutbox(UUID outboxId) {
        return deliveryOutboxJpaRepository.findById(outboxId);
    }
}
