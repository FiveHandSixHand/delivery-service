package com.fhsh.daitda.delivery.domain.exception;

import com.fhsh.daitda.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeliveryErrorCode implements ErrorCode {
    NOT_FOUND_DELIVERY(HttpStatus.NOT_FOUND, "배송을 찾을 수 없습니다."),
    INVALID_STATUS_TRANSITION(HttpStatus.BAD_REQUEST, "유효하지 않은 배송 상태 전환입니다."),
    CANNOT_CANCEL_DELIVERED(HttpStatus.BAD_REQUEST, "이미 완료된 배송은 취소할 수 없습니다."),
    ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 삭제된 배송입니다."),
    NOT_FOUND_DELIVERY_ROUTE(HttpStatus.NOT_FOUND, "배송경로를 찾을 수 없습니다."),
    DELIVERY_CREATION_FAILED(HttpStatus.BAD_REQUEST, "배송 생성을 실패하였습니다.");

    private final HttpStatus status;
    private final String description;
}
