package org.mitenkov.dto;

import org.mitenkov.enums.ErrorCode;

public record ErrorMessageDto(String message, ErrorCode errorCode) {

    public ErrorMessageDto(ErrorCode errorCode) {
        this(errorCode.getMessage(), errorCode);
    }
}
