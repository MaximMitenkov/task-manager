package org.mitenkov.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.mitenkov.enums.TaskType;

@Entity
@DiscriminatorValue("BUG")
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Bug extends Task {
    private String version;

    @Override
    public TaskType getTaskType() {
        return TaskType.BUG;
    }
}
