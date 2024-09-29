package org.mitenkov.helper;

import org.mitenkov.dto.CommentAddRequest;
import org.mitenkov.entity.Comment;

public class CommentConverter {

    public CommentAddRequest createAddRequest(Comment comment) {
        return CommentAddRequest.builder()
                .author(comment.getAuthor())
                .content(comment.getContent())
                .dateTime(comment.getDateTime())
                .taskId(comment.getId())
                .build();
    }

}
