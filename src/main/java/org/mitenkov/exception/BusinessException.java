package org.mitenkov.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mitenkov.enums.ErrorCode;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private ErrorCode errorCode;
}
