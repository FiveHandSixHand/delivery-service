package com.fhsh.daitda.delivery.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryOutbox {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID deliveryId;

    @Column(nullable = false)
    private UUID receiverHubId;

    @Column(nullable = false)
    private boolean processed = false;

    private int retryCount = 0;

    public DeliveryOutbox(UUID deliveryId, UUID receiverHubId) {
        this.deliveryId = deliveryId;
        this.receiverHubId = receiverHubId;
    }

    public void markAsProcessed() {
        this.processed = true;
    }

    public void incrementRetry() {
        this.retryCount++;
    }
}