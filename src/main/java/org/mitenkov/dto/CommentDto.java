package org.mitenkov.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentDto(
        int id,
        String content,
        String author,
        LocalDateTime dateTime,
        Integer taskId
) {
}
