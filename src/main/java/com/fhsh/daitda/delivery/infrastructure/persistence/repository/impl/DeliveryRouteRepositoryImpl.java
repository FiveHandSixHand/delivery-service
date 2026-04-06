package com.fhsh.daitda.delivery.infrastructure.persistence.repository.impl;

import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.entity.DeliveryRoute;
import com.fhsh.daitda.delivery.domain.repository.DeliveryRepository;
import com.fhsh.daitda.delivery.domain.repository.DeliveryRouteRepository;
import com.fhsh.daitda.delivery.infrastructure.persistence.repository.DeliveryJpaRepository;
import com.fhsh.daitda.delivery.infrastructure.persistence.repository.DeliveryRouteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeliveryRouteRepositoryImpl implements DeliveryRouteRepository {

    private final DeliveryRouteJpaRepository deliveryRouteJpaRepository;

    @Override
    public Optional<DeliveryRoute> findByDeliveryIdAndSequenceAndDeletedAtIsNull(UUID deliveryId, int sequence) {
        return deliveryRouteJpaRepository.findByDeliveryIdAndSequenceAndDeletedAtIsNull(deliveryId, sequence);
    }

    @Override
    public Slice<DeliveryRoute> findByDeliveryIdAndDeletedAtIsNull(UUID deliveryId, Pageable pageable) {
        return deliveryRouteJpaRepository.findByDeliveryIdAndDeletedAtIsNull(deliveryId, pageable);
    }
}
