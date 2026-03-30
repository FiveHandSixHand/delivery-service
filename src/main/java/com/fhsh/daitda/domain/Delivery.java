package com.fhsh.daitda.domain;

import com.fhsh.daitda.domain.status.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_delivery")
public class Delivery {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", nullable = false)
    private UUID id;

    private UUID orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private DeliveryStatus status = DeliveryStatus.HUB_WAITING;

    private UUID sourceHubId;

    private UUID destinationHubId;

    private UUID receiverTenantId;

    private UUID senderTenantId;

    private String senderTenantAddress;

    private String receiverTenantAddress;

    private UUID receiverId;

    private UUID senderID;
}
