package com.fhsh.daitda.delivery.presentation.controller;

import com.fhsh.daitda.delivery.application.service.query.DeliveryRouteQueryService;
import com.fhsh.daitda.delivery.presentation.dto.response.DeliveryRouteInfoResponse;
import com.fhsh.daitda.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryRouteController {

    private final DeliveryRouteQueryService deliveryRouteQueryService;

    @GetMapping("/{deliveryId}/routes/{sequence}")
    public ResponseEntity<CommonResponse<DeliveryRouteInfoResponse>> getDeliveryRoute(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Role") String role,
            @PathVariable UUID deliveryId,
            @PathVariable int sequence
    ) {
        DeliveryRouteInfoResponse response = DeliveryRouteInfoResponse.from(deliveryRouteQueryService.findDeliveryRoute(deliveryId, sequence));
        return ResponseEntity.ok(CommonResponse.success(response));
    }

    @GetMapping("/{deliveryId}/routes")
    public ResponseEntity<CommonResponse<Slice<DeliveryRouteInfoResponse>>> getDeliveries(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader(value = "X-User-Role") String role,
            @PathVariable UUID deliveryId,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Slice<DeliveryRouteInfoResponse> responses = deliveryRouteQueryService.getDeliveryRoutes(deliveryId, pageable)
                .map(DeliveryRouteInfoResponse::from);

        return ResponseEntity.ok(CommonResponse.success(responses));
    }
}
