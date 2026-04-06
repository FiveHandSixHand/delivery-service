package com.fhsh.daitda.delivery.infrastructure.state;

import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import com.fhsh.daitda.delivery.domain.exception.DeliveryErrorCode;
import com.fhsh.daitda.delivery.domain.state.DeliveryState;
import com.fhsh.daitda.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class CompleteState implements DeliveryState {

    @Override
    public void startTransit(Delivery delivery, String receiverEmail) {
        throw new BusinessException(DeliveryErrorCode.INVALID_STATUS_TRANSITION);
    }

    @Override
    public void arrive(Delivery delivery, String receiverEmail) {
        throw new BusinessException(DeliveryErrorCode.INVALID_STATUS_TRANSITION);
    }

    @Override
    public void startDelivering(Delivery delivery, String receiverEmail) {
        throw new BusinessException(DeliveryErrorCode.INVALID_STATUS_TRANSITION);
    }

    @Override
    public void complete(Delivery delivery, String receiverEmail) {
        throw new BusinessException(DeliveryErrorCode.INVALID_STATUS_TRANSITION);
    }

    @Override
    public DeliveryStatus getStatus() {
        return DeliveryStatus.COMPLETE;
    }
}