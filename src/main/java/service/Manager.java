package service;

import dao.FilterType;
import dao.SortType;
import dao.Storage;
import entities.Task;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@RequiredArgsConstructor
public class Manager {

    private final Storage storage;

    public void addTask(Task task) {
        storage.addTask(task);
    }

    public void removeTask(Task task) {
        storage.removeTask(task);
    }

    public ArrayList<Task> getFilteredTasks(FilterType type) {
        return storage.getFilteredTasks(type);
    }

    public ArrayList<Task> getSortedTasks(SortType type) {
        return storage.getSortedTasks(type);
    }

    public ArrayList<Task> getTasks() {
        return storage.getTasks();
    }
}
