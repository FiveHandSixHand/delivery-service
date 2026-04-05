package com.fhsh.daitda.delivery.presentation.dto.response;

import com.fhsh.daitda.delivery.application.result.DeliveryRouteResult;
import com.fhsh.daitda.delivery.domain.entity.DeliveryRoute;
import com.fhsh.daitda.delivery.domain.enums.DeliveryRouteStatus;
import com.fhsh.daitda.delivery.domain.enums.HubNodeType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeliveryRouteInfoResponse {

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

    public static DeliveryRouteInfoResponse from(DeliveryRouteResult deliveryRouteResult) {
        return new DeliveryRouteInfoResponse(
                deliveryRouteResult.getId(),
                deliveryRouteResult.getDeliveryId(),
                deliveryRouteResult.getStatus(),
                deliveryRouteResult.getSequence(),
                deliveryRouteResult.getDepartureNodeId(),
                deliveryRouteResult.getDepartureNodeType(),
                deliveryRouteResult.getDestinationNodeId(),
                deliveryRouteResult.getDestinationNodeType(),
                deliveryRouteResult.getDuration(),
                deliveryRouteResult.getDistance(),
                deliveryRouteResult.getEstimatedDuration(),
                deliveryRouteResult.getEstimatedDistance()
        );
    }
}
