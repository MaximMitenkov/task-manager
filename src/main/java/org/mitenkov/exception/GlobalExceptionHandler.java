package org.mitenkov.exception;

import lombok.extern.slf4j.Slf4j;
import org.mitenkov.dto.ErrorMessageDto;
import org.mitenkov.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorMessageDto> validationException(BusinessException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ErrorMessageDto messageDto = new ErrorMessageDto(errorCode.getMessage(), errorCode);
        return new ResponseEntity<>(messageDto, errorCode.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDto> exception(Exception ex) {
        log.error("Exception caught", ex);
        ErrorCode error = ErrorCode.UNKNOWN_ERROR;
        ErrorMessageDto dto = new ErrorMessageDto(error.getMessage(), error);
        return new ResponseEntity<>(dto, error.getHttpStatus());
    }

}
