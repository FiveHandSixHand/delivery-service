package com.fhsh.daitda.delivery.infrastructure.persistence.repository;

import com.fhsh.daitda.delivery.domain.entity.DeliveryOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeliveryOutboxJpaRepository extends JpaRepository<DeliveryOutbox, UUID> {
    List<DeliveryOutbox> findByProcessedFalseAndRetryCountLessThan(int maxRetryCount);
    boolean existsByDeliveryId(UUID deliveryId);
}
