package org.mitenkov.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "2.1.12")
    private String version;
}
