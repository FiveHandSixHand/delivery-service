package com.fhsh.daitda.delivery.application.service.command;

import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import com.fhsh.daitda.delivery.domain.exception.DeliveryErrorCode;
import com.fhsh.daitda.delivery.domain.repository.DeliveryRepository;
import com.fhsh.daitda.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryCommandServiceCancelDeleteTest {

    @InjectMocks
    private DeliveryCommandService deliveryCommandService;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Test
    void 배송_취소_성공() {
        // given
        UUID deliveryId = UUID.randomUUID();
        Delivery delivery = mock(Delivery.class);
        given(deliveryRepository.findById(any(UUID.class))).willReturn(Optional.of(delivery));

        // when
        deliveryCommandService.cancelDelivery(deliveryId);

        // then
        verify(delivery).cancel();
    }

    @Test
    void 배송완료_후_취소_실패() {
        // given
        UUID deliveryId = UUID.randomUUID();
        Delivery delivery = mock(Delivery.class);
        given(deliveryRepository.findById(deliveryId)).willReturn(Optional.of(delivery));
        doThrow(new BusinessException(DeliveryErrorCode.CANNOT_CANCEL_DELIVERED))
                .when(delivery).cancel();

        // when & then
        assertThatThrownBy(() -> deliveryCommandService.cancelDelivery(deliveryId))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 존재하지않는_배송_취소_실패() {
        // given
        UUID deliveryId = UUID.randomUUID();
        given(deliveryRepository.findById(deliveryId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> deliveryCommandService.cancelDelivery(deliveryId))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 배송_논리삭제_성공() {
        // given
        UUID deliveryId = UUID.randomUUID();
        Delivery delivery = mock(Delivery.class);
        given(deliveryRepository.findById(deliveryId)).willReturn(Optional.of(delivery));

        // when
        deliveryCommandService.deleteDelivery(deliveryId);

        // then
        verify(delivery).softDelete();
    }

    @Test
    void 이미_삭제된_배송_삭제_실패() {
        // given
        UUID deliveryId = UUID.randomUUID();
        Delivery delivery = mock(Delivery.class);
        given(deliveryRepository.findById(deliveryId)).willReturn(Optional.of(delivery));
        doThrow(new BusinessException(DeliveryErrorCode.ALREADY_DELETED))
                .when(delivery).softDelete();

        // when & then
        assertThatThrownBy(() -> deliveryCommandService.deleteDelivery(deliveryId))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void 존재하지않는_배송_삭제_실패() {
        // given
        UUID deliveryId = UUID.randomUUID();
        given(deliveryRepository.findById(deliveryId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> deliveryCommandService.deleteDelivery(deliveryId))
                .isInstanceOf(BusinessException.class);
    }
}