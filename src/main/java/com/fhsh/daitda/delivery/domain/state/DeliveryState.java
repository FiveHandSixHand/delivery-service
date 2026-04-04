package com.fhsh.daitda.delivery.domain.state;

import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;

public interface DeliveryState {

/*
    HubWaitingState    → startTransit() 만 가능
    HubInTransitState  → arrive() 만 가능
    HubArrivedState    → startDelivering() 만 가능
    DeliveringState    → complete() 만 가능
    CompleteState      → 모두 불가

    메서드 호출은 업무가 완료된 후 호출됩니다.
*/

    void startTransit(Delivery delivery, String receiverEmail);
    void arrive(Delivery delivery, String receiverEmail);
    void startDelivering(Delivery delivery, String receiverEmail);
    void complete(Delivery delivery, String receiverEmail);
    DeliveryStatus getStatus();
}