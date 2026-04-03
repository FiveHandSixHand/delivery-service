package com.fhsh.daitda.delivery.application.service.query;

import com.fhsh.daitda.delivery.application.result.DeliveryResult;
import com.fhsh.daitda.delivery.domain.entity.Delivery;
import com.fhsh.daitda.delivery.domain.exception.DeliveryErrorCode;
import com.fhsh.daitda.delivery.domain.repository.DeliveryRepository;
import com.fhsh.daitda.exception.BusinessException;
import com.fhsh.daitda.exception.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryQueryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryResult findDelivery(UUID deliveryId) {

        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY));

        return DeliveryResult.from(delivery);
    }

    //TODO 1. ROLE 값 임시 String 처리
    // 2. 기능 구현을 우선으로 하기 위해 SWITCH 문으로 ROLE 을 분기하지만,
    // OCP에는 위반되어보임. 추후에 전략패턴으로 리팩토링하여 완성도를 높임
    public Slice<DeliveryResult> getDeliveries(UUID userId, String role, Pageable pageable) {

        Slice<Delivery> deliveries = switch (role) {
            case "MASTER", "COMPANY_MANAGER" -> deliveryRepository.findAll(pageable);
            case "HUB_MANAGER" ->
//                    UUID hubId = hubClient.getHubIdByManagerId(userId); TODO hub데이터 가져오기 userId 임시로 사용 - hub에 api요청하기
                    deliveryRepository.findByHubId(userId, pageable);
            case "DELIVERY_MANAGER" -> deliveryRepository.findByDeliveryManagerId(userId, pageable);
            default -> throw new BusinessException(CommonErrorCode.FORBIDDEN);
        };

        return deliveries.map(DeliveryResult::from);
    }
}
