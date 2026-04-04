package com.fhsh.daitda.delivery.application.service.command;

import com.fhsh.daitda.delivery.application.state.DeliveryStateFactory;
import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import com.fhsh.daitda.delivery.domain.exception.DeliveryErrorCode;
import com.fhsh.daitda.delivery.domain.repository.DeliveryRepository;
import com.fhsh.daitda.delivery.infrastructure.state.HubWaitingState;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeliveryCommandServiceStatusTest {

    @InjectMocks
    private DeliveryCommandService deliveryCommandService;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private DeliveryStateFactory deliveryStateFactory;

    @Mock
    private HubWaitingState hubWaitingState;

    @Test
    void 배송_상태_변경_성공() {
        // given
        UUID deliveryId = UUID.randomUUID();
        Delivery delivery = mock(Delivery.class);
        given(delivery.getStatus()).willReturn(DeliveryStatus.HUB_WAITING);
        given(deliveryRepository.findById(deliveryId)).willReturn(Optional.of(delivery));
        given(deliveryStateFactory.getState(DeliveryStatus.HUB_WAITING)).willReturn(hubWaitingState);

        // when
        deliveryCommandService.updateStatus(deliveryId, DeliveryStatus.HUB_IN_TRANSIT, "test@test.com");

        // then
        verify(hubWaitingState).arrive(delivery, "test@test.com");
    }

    @Test
    void 잘못된_상태_전환_실패() {
        // given
        UUID deliveryId = UUID.randomUUID();
        Delivery delivery = mock(Delivery.class);
        given(delivery.getStatus()).willReturn(DeliveryStatus.COMPLETE);
        given(deliveryRepository.findById(deliveryId)).willReturn(Optional.of(delivery));
        given(deliveryStateFactory.getState(DeliveryStatus.COMPLETE))
                .willThrow(new BusinessException(DeliveryErrorCode.INVALID_STATUS_TRANSITION));

        // when & then
        assertThatThrownBy(() ->
                deliveryCommandService.updateStatus(deliveryId, DeliveryStatus.HUB_WAITING, "test@test.com"))
                .isInstanceOf(BusinessException.class);
    }
}