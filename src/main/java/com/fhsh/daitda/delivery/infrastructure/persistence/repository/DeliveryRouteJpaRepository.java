package com.fhsh.daitda.delivery.infrastructure.persistence.repository;

import com.fhsh.daitda.delivery.domain.entity.DeliveryRoute;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRouteJpaRepository extends JpaRepository<DeliveryRoute, UUID> {
    Optional<DeliveryRoute> findByDeliveryIdAndSequenceAndDeletedAtIsNull(UUID deliveryId, int sequence);
    Slice<DeliveryRoute> findByDeliveryIdAndDeletedAtIsNull(UUID deliveryId, Pageable pageable);
}
