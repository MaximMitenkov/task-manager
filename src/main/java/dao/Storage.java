package dao;

import entities.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

public class Storage {

    private final ArrayList<Task> storage = new ArrayList<>();
    private Integer idCounter = 0;

    public void addTask(Task task) {
        storage.add(idCounter++, task);
    }
    public void removeTask(int id) {
        storage.remove(id);
    }

    public Task[] getFilteredTasks(Predicate<Task> filter) {
        return storage.stream().filter(filter).toArray(Task[]::new);
    }

    public Task[] getSortedTasks(ArrayList<Comparator<Task>> comparators) {
        if (comparators.size() == 0) {
            return storage.toArray(new Task[storage.size()]);
        }
        ArrayList<Task> sortedStorage = storage;
        for (Comparator<Task> comparator : comparators) {
            sortedStorage.sort(comparator);
        }
        return sortedStorage.toArray(new Task[sortedStorage.size()]);
    }

}
