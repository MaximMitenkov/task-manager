package dao;

import entities.Bug;
import entities.Feature;
import entities.Task;
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
