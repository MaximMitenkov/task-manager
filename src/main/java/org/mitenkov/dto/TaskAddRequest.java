package org.mitenkov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.mitenkov.enums.Priority;
import org.mitenkov.enums.TaskType;

import java.time.LocalDate;

public record TaskAddRequest(
        @NotBlank(message = "Title is mandatory")
        String title,

        @Schema(example = "1.12.4")
        @Pattern(regexp = "[0-9]+\\.[0-9]+\\.[0-9]+",
                message = "Invalid Version pattern.Version pattern example: 1.12.4")
        String version,

        LocalDate deadline,
        Priority priority,
        TaskType type) {
}
