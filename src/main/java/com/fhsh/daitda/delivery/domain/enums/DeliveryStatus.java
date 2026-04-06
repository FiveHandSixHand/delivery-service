package com.fhsh.daitda.delivery.domain.enums;

import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.exception.DeliveryErrorCode;
import com.fhsh.daitda.delivery.domain.state.DeliveryState;
import com.fhsh.daitda.exception.BusinessException;

public enum DeliveryStatus {
    HUB_WAITING {
        @Override
        public void execute(DeliveryState state, Delivery delivery, String email) {
            state.startTransit(delivery, email);
        }
    },
    HUB_IN_TRANSIT {
        @Override
        public void execute(DeliveryState state, Delivery delivery, String email) {
            state.arrive(delivery, email);
        }
    },
    HUB_ARRIVED {
        @Override
        public void execute(DeliveryState state, Delivery delivery, String email) {
            state.startDelivering(delivery, email);
        }
    },
    DELIVERING {
        @Override
        public void execute(DeliveryState state, Delivery delivery, String email) {
            state.complete(delivery, email);
        }
    },
    COMPLETE {
        @Override
        public void execute(DeliveryState state, Delivery delivery, String email) {
            throw new BusinessException(DeliveryErrorCode.INVALID_STATUS_TRANSITION);
        }
    },
    CANCELLED {
        @Override
        public void execute(DeliveryState state, Delivery delivery, String email) {
            throw new BusinessException(DeliveryErrorCode.INVALID_STATUS_TRANSITION);
        }
    };

    public abstract void execute(DeliveryState state, Delivery delivery, String email);
}