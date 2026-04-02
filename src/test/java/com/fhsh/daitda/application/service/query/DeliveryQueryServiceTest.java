package com.fhsh.daitda.application.service.query;

import com.fhsh.daitda.application.result.DeliveryResult;
import com.fhsh.daitda.domain.entity.Delivery;
import com.fhsh.daitda.domain.repository.DeliveryRepository;
import com.fhsh.daitda.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeliveryQueryServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryQueryService deliveryQueryService;

    @Test
    @DisplayName("배송 단건 조회 - 성공")
    void findDelivery_Success() {
        // given
        UUID deliveryId = UUID.randomUUID();
        Delivery mockDelivery = Mockito.mock(Delivery.class);
        given(deliveryRepository.findById(deliveryId)).willReturn(Optional.of(mockDelivery));

        // when
        DeliveryResult result = deliveryQueryService.findDelivery(deliveryId);

        // then
        assertThat(result).isNotNull();
        verify(deliveryRepository).findById(deliveryId);
    }

    @Test
    @DisplayName("목록 조회 - MASTER 권한일 때 findAll 호출 확인")
    void getDeliveries_MasterRole() {
        // given
        UUID userId = UUID.randomUUID();
        String role = "MASTER";
        Pageable pageable = PageRequest.of(0, 10);

        Delivery mockDelivery = Mockito.mock(Delivery.class);
        Slice<Delivery> mockDeliveries = new SliceImpl<>(List.of(mockDelivery), pageable, false);

        given(deliveryRepository.findAll(pageable)).willReturn(mockDeliveries);

        // when
        Slice<DeliveryResult> results = deliveryQueryService.getDeliveries(userId, role, pageable);

        // then
        assertThat(results.getContent()).hasSize(1);
        verify(deliveryRepository).findAll(pageable);
    }

    @Test
    @DisplayName("목록 조회 - DELIVERY_MANAGER 권한일 때 findByDeliveryManagerId 호출 확인")
    void getDeliveries_DeliveryManagerRole() {
        // given
        UUID userId = UUID.randomUUID();
        String role = "DELIVERY_MANAGER";
        Pageable pageable = PageRequest.of(0, 10);

        Delivery mockDelivery = Mockito.mock(Delivery.class);
        Slice<Delivery> mockDeliveries = new SliceImpl<>(List.of(mockDelivery), pageable, false);

        given(deliveryRepository.findByDeliveryManagerId(userId, pageable)).willReturn(mockDeliveries);

        // when
        deliveryQueryService.getDeliveries(userId, role, pageable);

        // then
        verify(deliveryRepository).findByDeliveryManagerId(userId, pageable);
    }

    @Test
    @DisplayName("배송 단건 조회 - 데이터 없음 (예외 발생)")
    void findDelivery_NotFound() {
        // given
        UUID deliveryId = UUID.randomUUID();
        given(deliveryRepository.findById(deliveryId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> deliveryQueryService.findDelivery(deliveryId))
                .isInstanceOf(BusinessException.class);
    }
}