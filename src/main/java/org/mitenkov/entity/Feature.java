package org.mitenkov.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.mitenkov.enums.TaskType;

@Entity
@DiscriminatorValue("FEATURE")
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor
public class Feature extends Task {

    @Override
    public TaskType getTaskType() {
        return TaskType.FEATURE;
    }
}
