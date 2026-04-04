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
public class DeliveringState implements DeliveryState {

    private final SlackClient slackClient;

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
        delivery.changeStatus(DeliveryStatus.COMPLETE);
        // TODO: messageType 슬랙 담당자 확인 후 수정 필요
        slackClient.sendMessage(
                receiverEmail,
                String.format("[알림] 주문번호(%s)의 배송이 완료되었습니다.", delivery.getOrderId()),
                delivery.getOrderId(),
                "DELIVERY_COMPLETE"
        );
    }

    @Override
    public DeliveryStatus getStatus() {
        return DeliveryStatus.DELIVERING;
    }
}