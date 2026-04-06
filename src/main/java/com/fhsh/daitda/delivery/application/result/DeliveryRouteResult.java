package com.fhsh.daitda.delivery.application.result;

import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.entity.DeliveryRoute;
import com.fhsh.daitda.delivery.domain.enums.DeliveryRouteStatus;
import com.fhsh.daitda.delivery.domain.enums.HubNodeType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeliveryRouteResult {

    private UUID id;
    private UUID deliveryId;
    private DeliveryRouteStatus status;
    private int sequence;
    private UUID departureNodeId;
    private HubNodeType departureNodeType;
    private UUID destinationNodeId;
    private HubNodeType destinationNodeType;
    private int duration;
    private double distance;
    private int estimatedDuration;
    private double estimatedDistance;

    public static DeliveryRouteResult from(DeliveryRoute deliveryRoute) {
        return new DeliveryRouteResult(
                deliveryRoute.getId(),
                deliveryRoute.getDelivery().getId(),
                deliveryRoute.getStatus(),
                deliveryRoute.getSequence(),
                deliveryRoute.getDepartureNodeId(),
                deliveryRoute.getDepartureNodeType(),
                deliveryRoute.getDestinationNodeId(),
                deliveryRoute.getDestinationNodeType(),
                deliveryRoute.getDuration(),
                deliveryRoute.getDistance(),
                deliveryRoute.getEstimatedDuration(),
                deliveryRoute.getEstimatedDistance()
        );
    }
}
