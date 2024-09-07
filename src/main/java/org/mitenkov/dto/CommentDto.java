package org.mitenkov.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class CommentDto {

    private Integer id;
    private String content;
    private String author;
    private Timestamp dateTime;
    private Integer TaskId;

}
