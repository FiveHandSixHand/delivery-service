package com.fhsh.daitda.delivery.application.service.command;

import com.fhsh.daitda.delivery.application.client.response.CompanyHubInfoResponse;
import com.fhsh.daitda.delivery.application.client.response.HubRouteInfoResponse;
import com.fhsh.daitda.delivery.application.command.DeliveryCreateCommand;
import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.repository.DeliveryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DeliveryProcessorTest {

    @InjectMocks
    private DeliveryProcessor deliveryProcessor;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Test
    @DisplayName("배송 생성 시 배송경로가 함께 생성된다")
    void createAndSave_성공_배송경로생성() {
        // given
        UUID supplierHubId = UUID.randomUUID();
        UUID receiverHubId = UUID.randomUUID();

        DeliveryCreateCommand command = new DeliveryCreateCommand(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CompanyHubInfoResponse supplierHub = new CompanyHubInfoResponse(
                supplierHubId,
                "서울 공급업체 주소",
                LocalDateTime.now(),
                "홍길동"
        );

        CompanyHubInfoResponse receiverHub = new CompanyHubInfoResponse(
                receiverHubId,
                "부산 수령업체 주소",
                LocalDateTime.now(),
                "김철수"
        );

        HubRouteInfoResponse hubRouteInfo = new HubRouteInfoResponse(
                supplierHubId,
                receiverHubId,
                120,
                350.0
        );

        given(deliveryRepository.save(any(Delivery.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when
        Delivery result = deliveryProcessor.createAndSave(command, supplierHub, receiverHub, hubRouteInfo);

        // then
        assertThat(result.getDeliveryRoutes()).hasSize(1);
        assertThat(result.getDeliveryRoutes().get(0).getSequence()).isEqualTo(1);
        assertThat(result.getDeliveryRoutes().get(0).getDepartureNodeId()).isEqualTo(supplierHubId);
        assertThat(result.getDeliveryRoutes().get(0).getDestinationNodeId()).isEqualTo(receiverHubId);
    }
}