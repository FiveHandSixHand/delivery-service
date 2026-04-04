package com.fhsh.daitda.delivery.presentation.dto.request;

import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryStatusUpdateRequest {
    private DeliveryStatus status;
}