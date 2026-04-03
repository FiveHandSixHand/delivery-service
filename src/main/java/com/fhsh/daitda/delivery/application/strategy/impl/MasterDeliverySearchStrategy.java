package com.fhsh.daitda.delivery.application.strategy.impl;

import com.fhsh.daitda.delivery.application.strategy.DeliverySearchStrategy;
import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MasterDeliverySearchStrategy implements DeliverySearchStrategy {

    private final DeliveryRepository deliveryRepository;

    @Override
    public Slice<Delivery> search(UUID userId, Pageable pageable) {
        return deliveryRepository.findAll(pageable);
    }
}
