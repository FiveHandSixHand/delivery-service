package com.fhsh.daitda.delivery.infrastructure.persistence.repository.impl;

import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.entity.DeliveryOutbox;
import com.fhsh.daitda.delivery.domain.enums.DeliveryOutBoxStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DeliveryOutboxJpaRepository extends JpaRepository<DeliveryOutbox, UUID> {
    List<DeliveryOutbox> findByStatus(DeliveryOutBoxStatus status);
    DeliveryOutbox save(List<UUID> hubManagerIds);
}
