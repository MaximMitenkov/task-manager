package org.mitenkov.service;

import org.mitenkov.dao.FilterType;
import org.mitenkov.dao.SortType;
import org.mitenkov.dao.Storage;
import org.mitenkov.entities.Bug;
import org.mitenkov.entities.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Slf4j
@Service
@RequiredArgsConstructor
public class Manager {

    private final Storage storage;
    private final String versionRegex = "[0-9]+\\.[0-9]+\\.[0-9]+";
    private final Pattern versionPattern = Pattern.compile(versionRegex);

    public void addTask(Task task) throws PatternSyntaxException {
        if (task instanceof Bug) {
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
