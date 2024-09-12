package org.mitenkov.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mitenkov.enums.ErrorCode;

@Getter
@AllArgsConstructor
public class ErrorCodeException extends RuntimeException {
    private ErrorCode errorCode;
}
