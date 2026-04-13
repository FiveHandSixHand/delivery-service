package com.fhsh.daitda.delivery.infrastructure.persistence.repository.impl;

import com.fhsh.daitda.delivery.domain.entity.DeliveryOutbox;
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
    public List<DeliveryOutbox> findByStatus(DeliveryOutBoxStatus status) {
        return deliveryOutboxJpaRepository.findByStatus(status);
    }

    @Override
    public DeliveryOutbox save(DeliveryOutbox outbox) {
        return deliveryOutboxJpaRepository.save(outbox);
    }

    @Override
    public Optional<DeliveryOutbox> getDeliveryOutbox(UUID outboxId) {
        return deliveryOutboxJpaRepository.findById(outboxId);
    }
}
