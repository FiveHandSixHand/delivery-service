package com.fhsh.daitda.delivery.application.service.query;

import com.fhsh.daitda.delivery.application.result.DeliveryRouteResult;
import com.fhsh.daitda.delivery.domain.entity.DeliveryRoute;
import com.fhsh.daitda.delivery.domain.exception.DeliveryErrorCode;
import com.fhsh.daitda.delivery.domain.repository.DeliveryRouteRepository;
import com.fhsh.daitda.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryRouteQueryService {

    private final DeliveryRouteRepository deliveryRouteRepository;

    public DeliveryRouteResult findDeliveryRoute(UUID deliveryId, int sequence) {

        DeliveryRoute deliveryRoute = deliveryRouteRepository.findByDeliveryIdAndSequenceAndDeletedAtIsNull(deliveryId, sequence)
                .orElseThrow(() -> new BusinessException(DeliveryErrorCode.NOT_FOUND_DELIVERY_ROUTE));

        return DeliveryRouteResult.from(deliveryRoute);
    }

    public Slice<DeliveryRouteResult> getDeliveryRoutes(UUID deliveryId, Pageable pageable) {

        Slice<DeliveryRoute> deliveryRoutes = deliveryRouteRepository.findByDeliveryIdAndDeletedAtIsNull(deliveryId, pageable);
        return deliveryRoutes.map(DeliveryRouteResult::from);
    }
}
