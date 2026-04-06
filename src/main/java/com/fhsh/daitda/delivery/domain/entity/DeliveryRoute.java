package com.fhsh.daitda.delivery.domain.entity;

import com.fhsh.daitda.delivery.domain.enums.DeliveryRouteStatus;
import com.fhsh.daitda.delivery.domain.enums.HubNodeType;
import com.fhsh.daitda.delivery.domain.exception.DeliveryErrorCode;
import com.fhsh.daitda.delivery.domain.vo.HubRouteInfo;
import com.fhsh.daitda.domain.BaseUserEntity;
import com.fhsh.daitda.exception.BusinessException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    private HubNodeType departureNodeType;

    private UUID destinationNodeId;

    private HubNodeType destinationNodeType;

    private int duration;

    private double distance;

    private int estimatedDuration;

    private double estimatedDistance;

    private UUID deliveryManagerId;

    public static DeliveryRoute create(Delivery delivery, HubRouteInfo hubRouteInfo, int sequence) {
        DeliveryRoute deliveryRoute = new DeliveryRoute();

        deliveryRoute.delivery = delivery;
        deliveryRoute.status = DeliveryRouteStatus.HUB_WAITING;
        deliveryRoute.sequence = sequence;
        deliveryRoute.departureNodeId = hubRouteInfo.getSrcHubId();
        deliveryRoute.departureNodeType = HubNodeType.HUB;
        deliveryRoute.destinationNodeId = hubRouteInfo.getDestHubId();
        deliveryRoute.destinationNodeType = HubNodeType.HUB;

        // 생성 시점에 허브값 주입, 배송 완료 시 실제 값으로 업데이트
        deliveryRoute.duration = hubRouteInfo.getDuration();
        deliveryRoute.distance = hubRouteInfo.getDistance();

        //예상 시간과 거리는 허브간 거리 값 유지
        deliveryRoute.estimatedDuration = hubRouteInfo.getDuration();
        deliveryRoute.estimatedDistance = hubRouteInfo.getDistance();

        return deliveryRoute;
    }

    public void assignManager(UUID deliveryManagerId) {
        this.deliveryManagerId = deliveryManagerId;
    }

    public void softDelete(){
        super.delete(deletedBy);
    }

}
