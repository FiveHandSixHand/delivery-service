package com.fhsh.daitda.delivery.application.state;

import com.fhsh.daitda.delivery.domain.enums.DeliveryStatus;
import com.fhsh.daitda.delivery.domain.exception.DeliveryErrorCode;
import com.fhsh.daitda.delivery.domain.state.DeliveryState;
import com.fhsh.daitda.exception.BusinessException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DeliveryStateFactory {

    private final List<DeliveryState> states;
    private Map<DeliveryStatus, DeliveryState> stateMap;

    @PostConstruct
    public void init() {
        stateMap = states.stream()
                .collect(Collectors.toMap(
                        DeliveryState::getStatus,
                        state -> state
                ));
    }

    public DeliveryState getState(DeliveryStatus status) {
        if (status == DeliveryStatus.COMPLETE) {
            throw new BusinessException(DeliveryErrorCode.INVALID_STATUS_TRANSITION);
        }
        return Optional.ofNullable(stateMap.get(status))
                .orElseThrow(() -> new BusinessException(DeliveryErrorCode.INVALID_STATUS_TRANSITION));
    }
}
