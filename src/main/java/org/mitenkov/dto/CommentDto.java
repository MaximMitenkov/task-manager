package org.mitenkov.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Builder
public class CommentDto {

    @Setter(AccessLevel.NONE)
    private Integer id;

    private String content;
    private String author;
    private Timestamp dateTime;
    private Integer taskId;

}
