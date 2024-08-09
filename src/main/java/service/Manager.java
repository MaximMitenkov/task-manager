package service;

import dao.Storage;
import entities.Priority;
import entities.Task;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;

@Data
public class Manager {

    private final Storage storage;
    private Comparator[] comparators;
    private boolean[] sortFlags;


    public Manager(Storage storage) {
        this.storage = storage;

        comparators = new Comparator[3];
        comparators[0] = Comparator.comparing(Task::getTitle);
        comparators[1] = Comparator.comparing(Task::getPriority);
        comparators[2] = Comparator.comparing(Task::getDeadline);

        sortFlags = new boolean[comparators.length];
    }
    public void addTask(Task task) {
        storage.addTask(task);
    }

    public void removeTask(Task task) {
        storage.removeTask(task);
    }

    public Task[] getFilteredTasks(Class parameterClass) {
        return storage.getFilteredTasks(a -> a.getClass().equals(parameterClass));
    }

    public void changeSortFlag(int id) {
        sortFlags[id] = !sortFlags[id];
    }

    public Task[] getSortedTasks() {
        ArrayList<Comparator<Task>> comparatorsToStorage = new ArrayList<>();
        for (int i = 0; i < sortFlags.length; i++) {
            if (sortFlags[i])
                comparatorsToStorage.add(comparators[i]);
        }
        return storage.getSortedTasks(comparatorsToStorage);
    }
}
