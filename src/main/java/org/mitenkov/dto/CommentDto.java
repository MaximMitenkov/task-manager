package org.mitenkov.dto;

import java.sql.Timestamp;

public record CommentDto (Integer id, String content, String author, Timestamp dateTime, Integer taskId) {

}
