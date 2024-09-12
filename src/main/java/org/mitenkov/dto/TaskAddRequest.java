package org.mitenkov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.mitenkov.enums.Priority;
import org.mitenkov.enums.TaskType;

import java.time.LocalDate;

@Data
@Builder
public class TaskAddRequest {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @Pattern(regexp = "[0-9]+\\.[0-9]+\\.[0-9]+")
    private String version;

    private LocalDate deadline;
    private Priority priority;
    private TaskType type;

}
