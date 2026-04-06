package com.fhsh.daitda.delivery.domain.entity;

import com.fhsh.daitda.delivery.application.client.response.CompanyHubInfoResponse;
import com.fhsh.daitda.delivery.application.command.DeliveryCreateCommand;
import com.fhsh.daitda.delivery.domain.exception.DeliveryErrorCode;
import com.fhsh.daitda.domain.BaseUserEntity;
import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import com.fhsh.daitda.exception.BusinessException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_delivery")
public class Delivery extends BaseUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    public static Delivery create(DeliveryCreateCommand command, CompanyHubInfoResponse supplierHub, CompanyHubInfoResponse receiverHub) {
        Delivery delivery = new Delivery();
        delivery.orderId = command.getOrderId();
        delivery.status = DeliveryStatus.HUB_WAITING;
        delivery.departureHubId = supplierHub.getHubId();
        delivery.destinationHubId = receiverHub.getHubId();
        delivery.senderTenantId = command.getSupplierCompanyId();
        delivery.receiverTenantId = command.getReceiverCompanyId();
        delivery.senderTenantAddress = supplierHub.getFullAddress();
        delivery.receiverTenantAddress = receiverHub.getFullAddress();
        delivery.receiverId = receiverHub.getHubId();
        delivery.senderId = supplierHub.getHubId();
        return delivery;
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
