package com.fhsh.daitda.delivery.infrastructure.persistence.repository.impl;

import com.fhsh.daitda.delivery.domain.entity.DeliveryOutbox;
import com.fhsh.daitda.delivery.domain.repository.DeliveryOutboxRepository;
import com.fhsh.daitda.delivery.infrastructure.persistence.repository.DeliveryOutboxJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeliveryOutboxRepositoryImpl implements DeliveryOutboxRepository {

    private final DeliveryOutboxJpaRepository deliveryOutboxJpaRepository;

    @Override
    public List<DeliveryOutbox> findByProcessedFalseAndRetryCountLessThan(int maxRetryCount) {
        // JpaRepository의 쿼리 메서드를 호출하여 실제 데이터 조회
        return deliveryOutboxJpaRepository.findByProcessedFalseAndRetryCountLessThan(maxRetryCount);
    }

    @Override
    public boolean existsByDeliveryId(UUID deliveryId) {
        // 특정 배송 건에 대한 Outbox 기록 존재 여부 확인
        return deliveryOutboxJpaRepository.existsByDeliveryId(deliveryId);
    }

    @Override
    public void save(DeliveryOutbox deliveryOutbox) {
        deliveryOutboxJpaRepository.save(deliveryOutbox);
    }
}
