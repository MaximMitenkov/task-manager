package org.mitenkov.dto;

public record OutboxCommentMessageDto(
        String topic,
        CommentDto payload
) {
}
