package org.mitenkov.dao;

import org.mitenkov.entities.Bug;
import org.mitenkov.entities.Feature;
import org.mitenkov.entities.Task;
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
