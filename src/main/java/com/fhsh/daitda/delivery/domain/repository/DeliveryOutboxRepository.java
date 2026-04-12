package com.fhsh.daitda.delivery.domain.repository;

import com.fhsh.daitda.delivery.domain.entity.Delivery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository {
    Optional<Delivery> findById(UUID deliveryId);
    Slice<Delivery> findAll(Pageable pageable);
    Slice<Delivery> findByDeliveryManagerId(UUID deliveryManagerId, Pageable pageable);
    Slice<Delivery> findByHubId(UUID hubId, Pageable pageable);
    Delivery save(Delivery delivery);
}
