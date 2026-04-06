package com.fhsh.daitda.delivery.application.service.command;

import com.fhsh.daitda.delivery.application.client.CompanyClient;
import com.fhsh.daitda.delivery.application.client.DeliveryManagerClient;
import com.fhsh.daitda.delivery.application.client.response.CompanyHubInfoResponse;
import com.fhsh.daitda.delivery.application.command.DeliveryCreateCommand;
import com.fhsh.daitda.delivery.application.result.DeliveryCreateResult;
import com.fhsh.daitda.delivery.domain.entity.Delivery;
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
class DeliveryCommandServiceTest {

    @InjectMocks
    private DeliveryCommandService deliveryCommandService;

    @Mock
    private CompanyClient companyClient;

    @Mock
    private DeliveryManagerClient deliveryManagerClient;

    @Mock
    private DeliveryProcessor deliveryProcessor;

    @Test
    void 배송_생성_성공() {
        // given
        DeliveryCreateCommand command = new DeliveryCreateCommand(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        CompanyHubInfoResponse supplierHub = new CompanyHubInfoResponse(UUID.randomUUID(), "서울시", LocalDateTime.now(), "홍길동");
        CompanyHubInfoResponse receiverHub = new CompanyHubInfoResponse(UUID.randomUUID(), "부산시", LocalDateTime.now(), "김철수");
        Delivery delivery = Delivery.create(command, supplierHub, receiverHub);
        UUID managerId = UUID.randomUUID();

        given(companyClient.getHubIdByManagerId(command.getSupplierCompanyId())).willReturn(supplierHub);
        given(companyClient.getHubIdByManagerId(command.getReceiverCompanyId())).willReturn(receiverHub);
//        given(deliveryProcessor.createAndSave(command, supplierHub, receiverHub, hubRouteInfo)).willReturn(delivery);
        given(deliveryManagerClient.assignCompanyDeliveryManager(any(), any())).willReturn(managerId);
        given(deliveryProcessor.assignManager(delivery, managerId)).willReturn(DeliveryCreateResult.from(delivery));

        // when
        DeliveryCreateResult result = deliveryCommandService.registerDelivery(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getOrderId()).isEqualTo(command.getOrderId());
    }
}