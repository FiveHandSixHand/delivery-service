package com.fhsh.daitda.delivery.application.service.command;

import com.fhsh.daitda.delivery.application.client.CompanyClient;
import com.fhsh.daitda.delivery.application.client.DeliveryManagerClient;
import com.fhsh.daitda.delivery.application.client.response.CompanyHubInfo;
import com.fhsh.daitda.delivery.application.command.DeliveryCreateCommand;
import com.fhsh.daitda.delivery.application.result.DeliveryCreateResult;
import com.fhsh.daitda.delivery.domain.entity.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryCommandService {

    private final DeliveryManagerClient deliveryManagerClient;
    private final CompanyClient companyClient;
    private final DeliveryProcessor deliveryProcessor;

    public DeliveryCreateResult registerDelivery(DeliveryCreateCommand command) {

        // 공급업체와 수령업체 허브 정보 조회
        // TODO 외부 서비스 호출 실패 시 보상 로직 필요
        CompanyHubInfo supplierHub = companyClient.getHubIdByManagerId(command.getSupplierCompanyId());
        CompanyHubInfo receiverHub = companyClient.getHubIdByManagerId(command.getReceiverCompanyId());

        // DB 작업
        Delivery delivery = deliveryProcessor.createAndSave(command, supplierHub, receiverHub);

        // 담당자 배정
        UUID managerId = deliveryManagerClient.assignCompanyDeliveryManager(delivery.getId(), receiverHub.getHubId());

        // 담당자 업데이트
        return deliveryProcessor.assignManager(delivery, managerId);
    }
}
