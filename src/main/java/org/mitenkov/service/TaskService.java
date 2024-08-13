package org.mitenkov.service;

import org.mitenkov.enums.FilterType;
import org.mitenkov.enums.SortType;
import org.mitenkov.repository.TaskRepository;
import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final String versionRegex = "[0-9]+\\.[0-9]+\\.[0-9]+";
    private final Pattern versionPattern = Pattern.compile(versionRegex);

    public void addTask(Task task) throws PatternSyntaxException {
        if (task instanceof Bug) {
            Matcher matcher = versionPattern.matcher(((Bug) task).getVersion());
            if (!matcher.matches()) {
                throw new PatternSyntaxException("Incorrect format of version", versionRegex, 0);
            }
        }
        taskRepository.addTask(task);
    }

    public void removeTask(Task task) {
        taskRepository.removeTask(task);
    }

    public ArrayList<Task> getFilteredTasks(FilterType type) {
        return taskRepository.getFilteredTasks(type);
    }

    public ArrayList<Task> getSortedTasks(SortType type) {
        return taskRepository.getSortedTasks(type);
    }

    public ArrayList<Task> getTasks() {
        return taskRepository.getTasks();
    }
}
