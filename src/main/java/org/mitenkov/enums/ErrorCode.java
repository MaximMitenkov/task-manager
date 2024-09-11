package org.mitenkov.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ILLEGAL_VERSION("Версия не прошла валидацию", HttpStatus.BAD_REQUEST),
    ILLEGAL_TITLE("Заголовок не прошел валидацию", HttpStatus.BAD_REQUEST),
    ILLEGAL_DEADLINE("Дедлайн не прошёл валидацию", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED("Не хватает прав доступа", HttpStatus.FORBIDDEN),
    UNKNOWN_ERROR("Неизвестная ошибка", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus httpStatus;

}
