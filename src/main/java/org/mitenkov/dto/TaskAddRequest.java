package org.mitenkov.dto;

import lombok.Builder;
import lombok.Data;
import org.mitenkov.enums.Priority;
import org.mitenkov.enums.TaskType;

import java.time.LocalDate;

@Data
@Builder
public class TaskAddRequest {
    private String title;
    private LocalDate deadline;
    private Priority priority;
    private TaskType type;
    private String version;
}
