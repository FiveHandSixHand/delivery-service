package com.fhsh.daitda.delivery.domain.repository;

import com.fhsh.daitda.delivery.domain.entity.DeliveryRoute;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRouteRepository {
    Optional<DeliveryRoute> findByDeliveryIdAndSequenceAndDeletedAtIsNull(UUID deliveryId, int sequence);
    Slice<DeliveryRoute> findByDeliveryIdAndDeletedAtIsNull(UUID deliveryId, Pageable pageable);
}
