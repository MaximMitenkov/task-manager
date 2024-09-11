package org.mitenkov.exception;

import lombok.extern.slf4j.Slf4j;
import org.mitenkov.dto.ErrorMessageDto;
import org.mitenkov.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorCodeException.class)
    public ResponseEntity<ErrorMessageDto> validationException(ErrorCodeException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ErrorMessageDto messageDto = new ErrorMessageDto(errorCode.getMessage(), errorCode);
        return new ResponseEntity<>(messageDto, errorCode.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessageDto> validationException(MethodArgumentTypeMismatchException ex) {
        log.error("MethodArgumentTypeMismatchException", ex);
        ErrorCode error = ErrorCode.METHOD_TYPE_MISMATCH;
        ErrorMessageDto dto = new ErrorMessageDto(error.getMessage(), error);
        return new ResponseEntity<>(dto, error.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDto> exception(Exception ex) {
        log.error("Exception caught", ex);
        ErrorCode error = ErrorCode.UNKNOWN_ERROR;
        ErrorMessageDto dto = new ErrorMessageDto(error.getMessage(), error);
        return new ResponseEntity<>(dto, error.getHttpStatus());
    }

}
