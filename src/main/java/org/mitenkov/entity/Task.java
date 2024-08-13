package org.mitenkov.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.mitenkov.enums.Priority;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class Task {
    private String title;
    private Priority priority;
    private LocalDate deadline;
}
