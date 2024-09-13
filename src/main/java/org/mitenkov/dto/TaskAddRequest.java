package org.mitenkov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "1.12.4")
    @Pattern(regexp = "[0-9]+\\.[0-9]+\\.[0-9]+",
            message = "Invalid Version pattern. Version pattern example: 1.12.4")
    private String version;

    private LocalDate deadline;
    private Priority priority;
    private TaskType type;

}
