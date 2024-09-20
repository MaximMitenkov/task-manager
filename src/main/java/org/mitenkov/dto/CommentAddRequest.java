package org.mitenkov.dto;

import java.sql.Timestamp;

public record CommentAddRequest(
        String content,
        String author,
        Timestamp dateTime,
        Integer taskId
) {
}
