package org.mitenkov.enums;

import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Feature;
import org.mitenkov.entity.Task;
import lombok.Getter;

import java.util.function.Predicate;

@Getter
public enum FilterType {
    BUG(task -> task.getClass().equals(Bug.class)),
    FEATURE(task -> task.getClass().equals(Feature.class));

    private final Predicate<Task> predicate;

    FilterType(Predicate<Task> predicate) {
        this.predicate = predicate;
    }
}
