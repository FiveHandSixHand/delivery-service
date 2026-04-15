package com.fhsh.daitda.delivery.infrastructure.persistence.repository.impl;

import com.fhsh.daitda.delivery.domain.entity.Outbox;
import com.fhsh.daitda.delivery.domain.enums.DeliveryOutBoxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeliveryOutboxJpaRepository extends JpaRepository<Outbox, UUID> {
    List<Outbox> findByStatus(DeliveryOutBoxStatus status);
    Outbox save(List<UUID> hubManagerIds);
}
