package com.fhsh.daitda.delivery.domain.repository;

import com.fhsh.daitda.delivery.domain.entity.DeliveryOutbox;

import java.util.List;
import java.util.UUID;

public interface DeliveryOutboxRepository {

    /**
     * 아직 처리되지 않았으며(processed = false),
     * 설정한 최대 재시도 횟수보다 적게 시도된 메시지들을 조회합니다.
     * @param processed 처리 여부
     * @param maxRetryCount 최대 재시도 허용 횟수
     * @return 미처리된 Outbox 메시지 리스트
     */
    List<DeliveryOutbox> findByProcessedFalseAndRetryCountLessThan(int maxRetryCount);

    /**
     * 특정 배송 ID에 해당하는 Outbox 내역이 있는지 확인합니다.
     */
    boolean existsByDeliveryId(UUID deliveryId);

    void save(DeliveryOutbox deliveryOutbox);
}
