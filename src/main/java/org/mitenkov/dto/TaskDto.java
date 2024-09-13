package org.mitenkov.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.mitenkov.enums.Priority;
import org.mitenkov.enums.TaskType;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TaskDto(int id,
                      LocalDate deadline,
                      Priority priority,
                      TaskType taskType,
                      String title,

                      @Schema(example = "2.1.12")
                      String Version) {

}
