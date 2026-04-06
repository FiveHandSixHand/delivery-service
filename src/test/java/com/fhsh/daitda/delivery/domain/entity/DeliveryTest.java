package com.fhsh.daitda.delivery.domain.entity;

import com.fhsh.daitda.delivery.application.client.response.CompanyHubInfoResponse;
import com.fhsh.daitda.delivery.application.command.DeliveryCreateCommand;
import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import com.fhsh.daitda.delivery.domain.vo.HubRouteInfo;
import com.fhsh.daitda.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DeliveryTest {

    private DeliveryCreateCommand command;
    private CompanyHubInfoResponse supplierHub;
    private CompanyHubInfoResponse receiverHub;
    private HubRouteInfo hubRouteInfo;

    @BeforeEach
    void setUp() {
        command = new DeliveryCreateCommand(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        supplierHub = new CompanyHubInfoResponse(
                UUID.randomUUID(),
                "서울 공급업체 주소",
                LocalDateTime.now(),
                "홍길동"
        );

        receiverHub = new CompanyHubInfoResponse(
                UUID.randomUUID(),
                "부산 수령업체 주소",
                LocalDateTime.now(),
                "김철수"
        );

        hubRouteInfo = new HubRouteInfo(
                supplierHub.getHubId(),
                receiverHub.getHubId(),
                120,
                350.0
        );
    }

    @Test
    @DisplayName("배송 취소 시 연관 경로 전체가 논리적 삭제된다")
    void cancel_성공_배송경로논리적삭제() {
        // given
        Delivery delivery = Delivery.create(command, supplierHub, receiverHub);
        List<DeliveryRoute> routes = List.of(
                DeliveryRoute.create(delivery, hubRouteInfo, 1),
                DeliveryRoute.create(delivery, hubRouteInfo, 2)
        );
        delivery.addRoutes(routes);

        // when
        delivery.cancel();

        // then
        assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.CANCELLED);
        assertThat(delivery.getDeliveryRoutes())
                .allMatch(route -> route.getDeletedAt() != null);
    }

    @Test
    @DisplayName("완료된 배송은 취소할 수 없다")
    void cancel_실패_완료된배송() {
        // given
        Delivery delivery = Delivery.create(command, supplierHub, receiverHub);
        delivery.changeStatus(DeliveryStatus.COMPLETE);

        // when & then
        assertThatThrownBy(() -> delivery.cancel())
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("경로가 없는 배송 취소 시 정상 처리된다")
    void cancel_성공_경로없음() {
        // given
        Delivery delivery = Delivery.create(command, supplierHub, receiverHub);

        // when
        delivery.cancel();

        // then
        assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.CANCELLED);
        assertThat(delivery.getDeliveryRoutes()).isEmpty();
    }
}