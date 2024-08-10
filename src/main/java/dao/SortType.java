package dao;

import entities.Task;
import lombok.Getter;

import java.util.Comparator;

@Getter
public enum SortType {
    TITLE(Comparator.comparing(Task::getTitle)),
    PRIORITY(Comparator.comparing(Task::getPriority)),
    DEADLINE(Comparator.comparing(Task::getDeadline));

    private final Comparator<Task> comparator;

    SortType(Comparator<Task> comparator) {
        this.comparator = comparator;
    }

}
