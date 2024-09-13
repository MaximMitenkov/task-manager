package org.mitenkov.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.mitenkov.dto.ErrorMessageDto;
import org.mitenkov.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorCodeException.class)
    public ResponseEntity<ErrorMessageDto> validationException(ErrorCodeException ex) {
        ErrorCode error = ex.getErrorCode();
        return new ResponseEntity<>(new ErrorMessageDto(error), error.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessageDto> validationException(MethodArgumentTypeMismatchException ex) {
        log.error("MethodArgumentTypeMismatchException", ex);
        ErrorCode error = ErrorCode.METHOD_TYPE_MISMATCH;
        return new ResponseEntity<>(new ErrorMessageDto(error), error.getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessageDto> validationException(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException", ex);
        ErrorCode error = ErrorCode.CANNOT_PARSE_HTTP;
        return new ResponseEntity<>(new ErrorMessageDto(error), error.getHttpStatus());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessageDto> validationException(NoSuchElementException ex) {
        log.error("NoSuchElementException", ex);
        ErrorCode error = ErrorCode.NO_SUCH_ELEMENT;
        return new ResponseEntity<>(new ErrorMessageDto(error), error.getHttpStatus());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorMessageDto> validationException(NoResourceFoundException ex) {
        log.error("NoResourceFoundException", ex);
        ErrorCode error = ErrorCode.NO_SUCH_RESOURCE;
        return new ResponseEntity<>(new ErrorMessageDto(error), error.getHttpStatus());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorMessageDto> validationException(MissingServletRequestParameterException ex) {
        log.error("MissingServletRequestParameterException", ex);
        ErrorCode error = ErrorCode.NEED_PARAMETER;
        return new ResponseEntity<>(new ErrorMessageDto(error), error.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessageDto> validationException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException", ex);
        ErrorCode error = ErrorCode.VALIDATION_ERROR;
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().stream().findFirst().get();
        ErrorMessageDto messageDto = new ErrorMessageDto("Переданное значение: " +
                constraintViolation.getInvalidValue() +
                ". Ошибка: " + constraintViolation.getMessage(), error);
        return new ResponseEntity<>(messageDto, error.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDto> exception(Exception ex) {
        log.error("Exception caught", ex);
        ErrorCode error = ErrorCode.UNKNOWN_ERROR;
        return new ResponseEntity<>(new ErrorMessageDto(error), error.getHttpStatus());
    }

}
