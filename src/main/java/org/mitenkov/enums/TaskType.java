package org.mitenkov.enums;

import lombok.Getter;
import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Feature;
import org.mitenkov.entity.Task;

@Getter
public enum TaskType {

    BUG(Bug.class),
    FEATURE(Feature.class);

    private final Class<? extends Task> taskClass;

    TaskType(Class<? extends Task> taskClass) {
        this.taskClass = taskClass;
    }
}
