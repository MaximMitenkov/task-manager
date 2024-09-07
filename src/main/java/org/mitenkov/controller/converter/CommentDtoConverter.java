package org.mitenkov.controller.converter;

import lombok.RequiredArgsConstructor;
import org.mitenkov.dto.CommentDto;
import org.mitenkov.entity.Comment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDtoConverter {

    public CommentDto createDto(Comment comment) {
        return CommentDto.builder()
                .taskId(comment.getTask().getId())
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .dateTime(comment.getDateTime())
                .build();
    }

}
