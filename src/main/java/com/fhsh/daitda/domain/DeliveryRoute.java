package com.fhsh.daitda.domain;

import com.fhsh.daitda.domain.status.DeliveryRouteStatus;
import com.fhsh.daitda.domain.status.SourceNodeType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_delivery_route")
public class DeliveryRoute {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private DeliveryRouteStatus status = DeliveryRouteStatus.HUB_WAITING;

    private int sequence;

    private UUID sourceNodeId;

    private SourceNodeType sourceNodeType;

    private int duration;

    private double distance;

    private int estimatedDuration;

    private double estimatedDistance;

    private UUID deliveryManagerId;
}
