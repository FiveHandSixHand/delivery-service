package com.fhsh.daitda.delivery.presentation.controller;

import com.fhsh.daitda.delivery.application.result.DeliveryStatusUpdateResult;
import com.fhsh.daitda.delivery.application.result.DeliveryCreateResult;
import com.fhsh.daitda.delivery.application.service.command.DeliveryCommandService;
import com.fhsh.daitda.delivery.application.service.query.DeliveryQueryService;
import com.fhsh.daitda.delivery.presentation.dto.request.DeliveryStatusUpdateRequest;
import com.fhsh.daitda.delivery.presentation.dto.response.DeliveryCreateResponse;
import com.fhsh.daitda.delivery.presentation.dto.request.DeliveryCreateRequest;
import com.fhsh.daitda.delivery.presentation.dto.response.DeliveryInfoResponse;
import com.fhsh.daitda.delivery.presentation.dto.response.DeliveryStatusUpdateResponse;
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
public class DeliveryController {

    private final DeliveryQueryService deliveryQueryService;
    private final DeliveryCommandService deliveryCommandService;

    @GetMapping("/{deliveryId}")
    public ResponseEntity<CommonResponse<DeliveryInfoResponse>> getDelivery(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Role") String role,
            @PathVariable UUID deliveryId) {
        DeliveryInfoResponse response = DeliveryInfoResponse.from(deliveryQueryService.findDelivery(deliveryId));
        return ResponseEntity.ok(CommonResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<Slice<DeliveryInfoResponse>>> getDeliveries(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader(value = "X-User-Role") String role,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Slice<DeliveryInfoResponse> responses = deliveryQueryService.getDeliveries(userId, role, pageable)
                .map(DeliveryInfoResponse::from);

        return ResponseEntity.ok(CommonResponse.success(responses));
    }

    @PostMapping
    public ResponseEntity<CommonResponse<DeliveryCreateResponse>> createDelivery(
            @RequestBody DeliveryCreateRequest request
    ) {
        DeliveryCreateResult result = deliveryCommandService.registerDelivery(request.toCommand());
        return ResponseEntity.ok(CommonResponse.success(DeliveryCreateResponse.from(result)));
    }

    @PatchMapping("/{deliveryId}/status")
    public ResponseEntity<CommonResponse<DeliveryStatusUpdateResponse>> updateStatus(
            @PathVariable UUID deliveryId,
            @RequestBody DeliveryStatusUpdateRequest request,
            @RequestHeader(value = "X-User-Email") String email) {

        DeliveryStatusUpdateResult result = deliveryCommandService.updateStatus(deliveryId, request.getStatus(), email);
        return ResponseEntity.ok(CommonResponse.success(DeliveryStatusUpdateResponse.from(result)));
    }

    @DeleteMapping("/{deliveryId}/cancel")
    public ResponseEntity<CommonResponse<Void>> cancelDelivery(
            @PathVariable UUID deliveryId) {

        deliveryCommandService.cancelDelivery(deliveryId);
        return ResponseEntity.ok(CommonResponse.success(null));
    }
}
