package com.fhsh.daitda.application.strategy;

import com.fhsh.daitda.domain.entity.Delivery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface DeliverySearchStrategy {
    Slice<Delivery> search(UUID userId, Pageable pageable);
}
