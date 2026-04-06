package com.fhsh.daitda.delivery.infrastructure.state;

import com.fhsh.daitda.delivery.application.client.SlackClient;
import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import com.fhsh.daitda.delivery.domain.exception.DeliveryErrorCode;
import com.fhsh.daitda.delivery.domain.state.DeliveryState;
import com.fhsh.daitda.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubWaitingState implements DeliveryState {

    /*
    * 허브 대기 -> 허브 이동
    * */

    private final SlackClient slackClient;

    @Override
    public void startTransit(Delivery delivery, String receiverEmail) {
        delivery.changeStatus(DeliveryStatus.HUB_IN_TRANSIT);
        slackClient.sendMessage(
                receiverEmail,
                String.format("[알림] 주문번호(%s)의 허브 이동이 시작되었습니다.", delivery.getOrderId()),
                delivery.getOrderId(),
                "HUB_TRANSIT_START"
        );
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

    }


    @Override
    public DeliveryStatus getStatus() {
        return DeliveryStatus.HUB_WAITING;
    }
}
