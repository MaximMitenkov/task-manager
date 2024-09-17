package org.mitenkov.service;

import jakarta.validation.Valid;
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
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Validated
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskValidator taskValidationService;
    private final TaskRepository taskRepository;

    public Task addTask(@Valid TaskAddRequest request) {
        taskValidationService.validateTitleLength(request.title());

        return switch (request.type()) {
            case BUG -> {
                String version = request.version();
                taskValidationService.validateVersionNumber(version);
                yield taskRepository.save(Bug.builder()
                        .title(request.title())
                        .comments(new ArrayList<>())
                        .priority(request.priority())
                        .deadline(request.deadline())
                        .version(request.version())
                        .build());
            }
            case FEATURE -> {
                taskValidationService.validateDeadline(request.deadline());
                yield  taskRepository.save(Feature.builder()
                        .title(request.title())
                        .comments(new ArrayList<>())
                        .priority(request.priority())
                        .deadline(request.deadline())
                        .build());
            }
        };
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

    public Task getTaskById(int id) {
        return taskRepository.findById(id).orElseThrow(NoSuchElementException::new);
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
