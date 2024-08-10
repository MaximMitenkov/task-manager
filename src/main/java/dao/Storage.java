package dao;

import entities.Task;

import java.util.ArrayList;

public class Storage {

    private final ArrayList<Task> storage = new ArrayList<>();

    public void addTask(Task task) {
        storage.add(task);
    }

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
