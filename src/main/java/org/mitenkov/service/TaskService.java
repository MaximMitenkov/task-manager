package org.mitenkov.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Feature;
import org.mitenkov.entity.Task;
import org.mitenkov.enums.FilterType;
import org.mitenkov.enums.SortType;
import org.mitenkov.repository.TaskRepository;
import org.mitenkov.service.validator.TaskValidator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.PatternSyntaxException;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskValidator taskValidationService;
    private final TaskRepository taskRepository;

    public void addTask(Task task) throws PatternSyntaxException {
        taskValidationService.validateTitleLength(task.getTitle());
        if (task instanceof Bug bug) {
            String version = bug.getVersion();
            taskValidationService.validateVersionFormat(version);
            taskValidationService.validateVersionNumber(version);
        }
        if (task instanceof Feature) {
            taskValidationService.validateDeadline(task.getDeadline());
        }
        taskRepository.save(task);
    }

    public void removeTask(Task task) {
        taskRepository.delete(task);
    }

    public List<Task> getFilteredTasks(FilterType type) {
        return taskRepository.getFilteredTasks(type.getFilterClass());
    }

    public List<Task> getSortedTasks(SortType type) {
        return taskRepository.findAll(Sort.by(chooseSort(type), type.getColumn()));
    }

    public List<Task> getSortedAndFilteredTasks(FilterType type, SortType sortType) {
        return taskRepository.getFilteredTasks(
                type.getFilterClass(),
                Sort.by(chooseSort(sortType), sortType.getColumn()));
    }

    private Sort.Direction chooseSort(SortType sortType) {
        return switch (sortType) {
            case PRIORITY -> Sort.Direction.DESC;
            default -> Sort.Direction.ASC;
        };
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }
}
