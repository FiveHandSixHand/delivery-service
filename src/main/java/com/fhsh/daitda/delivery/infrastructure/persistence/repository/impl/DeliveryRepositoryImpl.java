package com.fhsh.daitda.delivery.infrastructure.persistence.repository.impl;

import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.repository.DeliveryRepository;
import com.fhsh.daitda.delivery.infrastructure.persistence.repository.DeliveryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final DeliveryJpaRepository deliveryJpaRepository;

    @Override
    public Slice<Delivery> findAll(Pageable pageable) {
        return deliveryJpaRepository.findAll(pageable);
    }

    @Override
    public Optional<Delivery> findById(UUID deliveryId) {
        return deliveryJpaRepository.findById(deliveryId);
    }

    @Override
    public Slice<Delivery> findByDeliveryManagerId(UUID deliveryManagerId, Pageable pageable) {
        return deliveryJpaRepository.findByDeliveryManagerId(deliveryManagerId, pageable);
    }

    @Override
    public Slice<Delivery> findByHubId(UUID hubId, Pageable pageable) {
        return deliveryJpaRepository.findByHubId(hubId, pageable);
    }

    @Override
    public Delivery save(Delivery delivery) {
        return deliveryJpaRepository.save(delivery);
    }
}
