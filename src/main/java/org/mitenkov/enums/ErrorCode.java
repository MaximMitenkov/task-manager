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
    UNKNOWN_ERROR("Неизвестная ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
    //    UNKNOWN_TASK_TYPE("Неизвестный тип задачи", HttpStatus.BAD_REQUEST),
    //    UNKNOWN_TASK_SORT("Неизвестный тип сортировки задач", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

}
