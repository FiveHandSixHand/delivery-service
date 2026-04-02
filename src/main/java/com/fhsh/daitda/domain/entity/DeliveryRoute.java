package com.fhsh.daitda.domain.entity;

import com.fhsh.daitda.domain.BaseUserEntity;
import com.fhsh.daitda.domain.enums.DeliveryRouteStatus;
import com.fhsh.daitda.domain.enums.DepartureNodeType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_delivery_route")
public class DeliveryRoute extends BaseUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private DeliveryRouteStatus status = DeliveryRouteStatus.HUB_WAITING;

    private int sequence;

    private UUID departureNodeId;

    private DepartureNodeType departureNodeType;

    private int duration;

    private double distance;

    private int estimatedDuration;

    private double estimatedDistance;

    private UUID deliveryManagerId;
}
