package org.mitenkov.dto;

import java.time.LocalDateTime;

public record CommentAddRequest(
        String content,
        String author,
        LocalDateTime dateTime,
        Integer taskId
) {
}
