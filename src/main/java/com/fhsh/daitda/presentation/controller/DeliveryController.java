package com.fhsh.daitda.presentation.controller;

import com.fhsh.daitda.application.service.query.DeliveryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

    private final DeliveryQueryService deliveryQueryService;

//    @GetMapping("/{deliveryId}")
//    public ResponseEntity<CommonResponse<DeliveryCreateResponse>> getDelivery(){
//        return ResponseEntity.ok(CommonResponse.success());
//    }
}
