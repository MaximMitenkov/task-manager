package org.mitenkov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.mitenkov.enums.ErrorCode;

@Data
@AllArgsConstructor
public class ErrorMessageDto {

    private String message;
    private ErrorCode errorCode;

    public ErrorMessageDto(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
