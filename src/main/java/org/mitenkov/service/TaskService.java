package org.mitenkov.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mitenkov.dto.TaskAddRequest;
import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Comment;
import org.mitenkov.entity.Feature;
import org.mitenkov.entity.Task;
import org.mitenkov.enums.SortType;
import org.mitenkov.enums.TaskType;
import org.mitenkov.repository.TaskRepository;
import org.mitenkov.service.validator.TaskValidator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskValidator taskValidationService;
    private final TaskRepository taskRepository;

    public void addTask(TaskAddRequest request) throws PatternSyntaxException {
        taskValidationService.validateTitleLength(request.getTitle());

        switch (request.getType()) {
            case BUG -> {
                String version = request.getVersion();
                taskValidationService.validateVersionFormat(version);
                taskValidationService.validateVersionNumber(version);
                taskRepository.save(Bug.builder()
                        .title(request.getTitle())
                        .comments(new ArrayList<>())
                        .priority(request.getPriority())
                        .deadline(request.getDeadline())
                        .version(request.getVersion())
                        .build());
            }
            case FEATURE -> {
                taskValidationService.validateDeadline(request.getDeadline());
                taskRepository.save(Feature.builder()
                        .title(request.getTitle())
                        .comments(new ArrayList<>())
                        .priority(request.getPriority())
                        .deadline(request.getDeadline())
                        .build());
            }
        }
    }

    public void removeTask(int id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getFilteredTasks(TaskType type) {
        if (type == null) {
            return getTasks();
        }
        return taskRepository.getFilteredTasks(type.getTaskClass());
    }

    public List<Task> getSortedTasks(SortType type) {
        if (type == null) {
            return getTasks();
        }
        return taskRepository.findAll(Sort.by(chooseSort(type), type.getColumn()));
    }

    public List<Task> getSortedAndFilteredTasks(TaskType type, SortType sort) {
        if (type == null) {
            return getSortedTasks(sort);
        }
        if (sort == null) {
            return getFilteredTasks(type);
        }
        return taskRepository.getFilteredTasks(
                type.getTaskClass(),
                Sort.by(chooseSort(sort), sort.getColumn()));
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

    public List<Comment> findCommentsByTaskId(Task task) {
        return taskRepository.findCommentsByTaskId(task.getId());
    }
}
