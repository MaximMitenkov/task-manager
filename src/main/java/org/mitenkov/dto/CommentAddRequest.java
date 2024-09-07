package org.mitenkov.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class CommentAddRequest {

    private String content;
    private String author;
    private Timestamp dateTime;
    private Integer TaskId;

}
