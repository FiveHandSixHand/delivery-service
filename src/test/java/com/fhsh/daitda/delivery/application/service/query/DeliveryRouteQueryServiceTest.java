package com.fhsh.daitda.delivery.application.service.query;

import com.fhsh.daitda.delivery.application.client.response.CompanyHubInfoResponse;
import com.fhsh.daitda.delivery.application.command.DeliveryCreateCommand;
import com.fhsh.daitda.delivery.application.result.DeliveryRouteResult;
import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.entity.DeliveryRoute;
import com.fhsh.daitda.delivery.domain.repository.DeliveryRouteRepository;
import com.fhsh.daitda.delivery.domain.vo.HubRouteInfo;
import com.fhsh.daitda.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DeliveryRouteQueryServiceTest {

    @InjectMocks
    private DeliveryRouteQueryService deliveryRouteQueryService;

    @Mock
    private DeliveryRouteRepository deliveryRouteRepository;

    private Delivery delivery;
    private HubRouteInfo hubRouteInfo;

    @BeforeEach
    void setUp() {
        DeliveryCreateCommand command = new DeliveryCreateCommand(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()
        );
        CompanyHubInfoResponse supplierHub = new CompanyHubInfoResponse(
                UUID.randomUUID(), "서울 공급업체 주소", LocalDateTime.now(), "홍길동"
        );
        CompanyHubInfoResponse receiverHub = new CompanyHubInfoResponse(
                UUID.randomUUID(), "부산 수령업체 주소", LocalDateTime.now(), "김철수"
        );
        delivery = Delivery.create(command, supplierHub, receiverHub);
        hubRouteInfo = new HubRouteInfo(
                supplierHub.getHubId(), receiverHub.getHubId(), 120, 350.0
        );
    }

    @Test
    @DisplayName("배송경로 단건 정상 반환")
    void findDeliveryRoute_성공() {
        // given
        UUID deliveryId = UUID.randomUUID();
        int sequence = 1;
        DeliveryRoute mockRoute = DeliveryRoute.create(delivery, hubRouteInfo, sequence);

        given(deliveryRouteRepository.findByDeliveryIdAndSequenceAndDeletedAtIsNull(deliveryId, sequence))
                .willReturn(Optional.of(mockRoute));

        // when
        DeliveryRouteResult result = deliveryRouteQueryService.findDeliveryRoute(deliveryId, sequence);

        // then
        assertThat(result.getSequence()).isEqualTo(1);
        assertThat(result.getDepartureNodeId()).isEqualTo(hubRouteInfo.getSrcHubId());
    }

    @Test
    @DisplayName("배송경로 목록 정상 반환")
    void getDeliveryRoutes_성공() {
        // given
        UUID deliveryId = UUID.randomUUID();
        List<DeliveryRoute> mockRoutes = List.of(
                DeliveryRoute.create(delivery, hubRouteInfo, 1),
                DeliveryRoute.create(delivery, hubRouteInfo, 2)
        );
        Slice<DeliveryRoute> mockSlice = new SliceImpl<>(mockRoutes, PageRequest.of(0, 10), false);

        given(deliveryRouteRepository.findByDeliveryIdAndDeletedAtIsNull(deliveryId, PageRequest.of(0, 10)))
                .willReturn(mockSlice);

        // when
        Slice<DeliveryRouteResult> result = deliveryRouteQueryService.getDeliveryRoutes(deliveryId, PageRequest.of(0, 10));

        // then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getSequence()).isEqualTo(1);
        assertThat(result.getContent().get(1).getSequence()).isEqualTo(2);
    }

    @Test
    @DisplayName("존재하지 않는 배송경로 단건 조회 시 예외 반환")
    void findDeliveryRoute_실패_없는경로() {
        // given
        UUID deliveryId = UUID.randomUUID();
        int sequence = 999;

        given(deliveryRouteRepository.findByDeliveryIdAndSequenceAndDeletedAtIsNull(deliveryId, sequence))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> deliveryRouteQueryService.findDeliveryRoute(deliveryId, sequence))
                .isInstanceOf(BusinessException.class);
    }
}