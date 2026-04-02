package com.fhsh.daitda.presentation.controller;

import com.fhsh.daitda.application.service.query.DeliveryQueryService;
import com.fhsh.daitda.presentation.dto.response.DeliveryInfoResponse;
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

    @GetMapping("/{deliveryId}")
    public ResponseEntity<CommonResponse<DeliveryInfoResponse>> getDelivery(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader(value = "X-User-Role", defaultValue = "MASTER") String role, // TODO: 인증인가 개발 후 하드코딩 삭제
            @PathVariable UUID deliveryId) {
        DeliveryInfoResponse response = DeliveryInfoResponse.from(deliveryQueryService.findDelivery(deliveryId));
        return ResponseEntity.ok(CommonResponse.success(response));
    }

    // TODO User아이디 적용
    @GetMapping
    public ResponseEntity<CommonResponse<Slice<DeliveryInfoResponse>>> getDeliveries(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader(value = "X-User-Role", defaultValue = "MASTER") String role, // TODO: 인증인가 개발 후 하드코딩 삭제
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Slice<DeliveryInfoResponse> responses = deliveryQueryService.getDeliveries(userId, role, pageable)
                .map(DeliveryInfoResponse::from);

        return ResponseEntity.ok(CommonResponse.success(responses));
    }
}
