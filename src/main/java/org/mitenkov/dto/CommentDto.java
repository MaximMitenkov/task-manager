package org.mitenkov.dto;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record CommentDto (Integer id,
                          String content,
                          String author,
                          Timestamp dateTime,
                          Integer taskId) {

}
