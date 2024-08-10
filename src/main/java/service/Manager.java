package service;

import dao.FilterType;
import dao.SortType;
import dao.Storage;
import entities.Bug;
import entities.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Slf4j
@RequiredArgsConstructor
public class Manager {

    private final Storage storage;

    public void addTask(Task task) throws PatternSyntaxException {
        if (task instanceof Bug) {
            String versionRegex = "[0-9]+\\.[0-9]+\\.[0-9]+";
            Pattern versionPattern = Pattern.compile(versionRegex);
            Matcher matcher = versionPattern.matcher(((Bug) task).getVersion());
            if (!matcher.matches()) {
                throw new PatternSyntaxException("Incorrect format of version", versionRegex, 0);
            }
        }
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
