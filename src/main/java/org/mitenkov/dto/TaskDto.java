package org.mitenkov.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.mitenkov.enums.Priority;
import org.mitenkov.enums.TaskType;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class TaskDto {
    private int id;
    private LocalDate deadline;
    private Priority priority;
    private TaskType type;
    private String title;
    private String version;
}
