package dao;

import entities.Task;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Storage {

    private final ArrayList<Task> storage = new ArrayList<>();
    private Integer idCounter = 0;

    public void addTask(Task task) {
        storage.add(idCounter++, task);
    }

    //TODO Перенести в Storage сортировку и фильтр через енамы

    public void removeTask(Task task) {
        storage.remove(task);
    }

    public ArrayList<Task> getFilteredTasks(FilterType type) {
        return new ArrayList<>(storage.stream().filter(type.getPredicate()).toList());
    }

    public ArrayList<Task> getSortedTasks(SortType type) {
        return new ArrayList<>(storage.stream().sorted(type.getComparator()).toList());
    }

    public ArrayList<Task> getTasks() {
        return storage;
    }

}
