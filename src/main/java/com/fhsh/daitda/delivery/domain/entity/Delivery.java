package com.fhsh.daitda.delivery.domain.entity;

import com.fhsh.daitda.delivery.domain.exception.DeliveryErrorCode;
import com.fhsh.daitda.domain.BaseUserEntity;
import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import com.fhsh.daitda.exception.BusinessException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_delivery")
public class Delivery extends BaseUserEntity {

    @Id
    private UUID id;

    private UUID orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private DeliveryStatus status = DeliveryStatus.HUB_WAITING;

    @OneToMany(mappedBy = "delivery", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<DeliveryRoute> deliveryRoutes = new ArrayList<>();

    private UUID departureHubId;

    private UUID destinationHubId;

    private UUID receiverTenantId;

    private UUID senderTenantId;

    private String receiverTenantAddress;

    private String senderTenantAddress;

    private UUID receiverId;

    private UUID senderId;

    private UUID deliveryManagerId;

    @Builder(access = AccessLevel.PRIVATE)
    private Delivery(UUID id, UUID orderId, UUID departureHubId, UUID destinationHubId, UUID senderTenantId, UUID receiverTenantId, String senderTenantAddress, String receiverTenantAddress, UUID receiverId, UUID senderId) {
        this.id = id;
        this.orderId = orderId;
        this.departureHubId = departureHubId;
        this.destinationHubId = destinationHubId;
        this.senderTenantId = senderTenantId;
        this.receiverTenantId = receiverTenantId;
        this.senderTenantAddress = senderTenantAddress;
        this.receiverTenantAddress = receiverTenantAddress;
        this.receiverId = receiverId;
        this.senderId = senderId;
    }

    public static Delivery create(UUID deliveryId, UUID orderId, UUID departureHubId, UUID destinationHubId, UUID senderTenantId, UUID receiverTenantId, String senderTenantFullAddress, String receiverTenantFullAddress, UUID receiverId, UUID senderId) {
        return Delivery.builder()
                .id(deliveryId)
                .orderId(orderId)
                .departureHubId(departureHubId)
                .destinationHubId(destinationHubId)
                .senderTenantId(senderTenantId)
                .receiverTenantId(receiverTenantId)
                .senderTenantAddress(senderTenantFullAddress)
                .receiverTenantAddress(receiverTenantFullAddress)
                .receiverId(receiverId)
                .senderId(senderId)
                .build();
    }

    // 최종 허브 -> 업체
    public void assignManagers(UUID companyDeliveryManagerId) {
        this.deliveryManagerId = companyDeliveryManagerId;
    }

    public void changeStatus(DeliveryStatus newStatus) {
        this.status = newStatus;
    }

    public void cancel() {
        if (this.status == DeliveryStatus.COMPLETE) {
            throw new BusinessException(DeliveryErrorCode.CANNOT_CANCEL_DELIVERED);
        }
        this.status = DeliveryStatus.CANCELLED;

        this.deliveryRoutes.stream()
                .filter(deliveryRoute -> deliveryRoute.getDeletedAt() == null)
                .forEach(DeliveryRoute::softDelete);
    }

    public void addRoutes(List<DeliveryRoute> routes) {
        this.deliveryRoutes.addAll(routes);
    }

    public void softDelete(){
        super.delete(deletedBy);
    }
}
