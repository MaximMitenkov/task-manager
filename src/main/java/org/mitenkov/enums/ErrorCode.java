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
    TASK_CREATION_ERROR("Ошибка в ходе создания задачи", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED("Не хватает прав доступа", HttpStatus.FORBIDDEN),
    METHOD_TYPE_MISMATCH("Передан агрумент не подходящего типа", HttpStatus.BAD_REQUEST),
    CANNOT_PARSE_HTTP("Ошибка десериализации HTTP", HttpStatus.BAD_REQUEST),
    UNKNOWN_ERROR("Неизвестная ошибка", HttpStatus.INTERNAL_SERVER_ERROR),
    NO_SUCH_ELEMENT("Требуемый элемент не был найден", HttpStatus.NOT_FOUND),
    NO_SUCH_RESOURCE("Требуемый ресурс не найден", HttpStatus.NOT_FOUND),
    VALIDATION_ERROR("Ошибка валидации", HttpStatus.BAD_REQUEST),
    NEED_PARAMETER("Требуется указать параметр", HttpStatus.BAD_REQUEST),
    NOT_UNIQUE("Переданное значение не соответствует ограничению на уникальность", HttpStatus.BAD_REQUEST),
    NOT_CURRENT_USER("Попытка изменения не авторизованного пользователя", HttpStatus.BAD_REQUEST),;

    private final String message;
    private final HttpStatus httpStatus;

}
