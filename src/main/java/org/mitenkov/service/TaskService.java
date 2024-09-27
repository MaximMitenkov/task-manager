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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
                        .priority(request.priority())
                        .deadline(request.deadline())
                        .version(request.version())
                        .build());
            }
            case FEATURE -> {
                taskValidationService.validateDeadline(request.deadline());
                yield taskRepository.save(Feature.builder()
                        .title(request.title())
                        .priority(request.priority())
                        .deadline(request.deadline())
                        .build());
            }
        };
    }

    public void removeTask(int id) {
        taskRepository.deleteById(id);
    }

    public Page<Task> getFilteredTasks(TaskType type, Pageable pageable) {
        if (type == null) {
            return getTasks(pageable);
        }
        return taskRepository.getFilteredTasks(type.getTaskClass(), pageable);
    }

    public Page<Task> getSortedTasks(SortType type, Pageable pageable) {
        if (type == null) {
            return getTasks(pageable);
        }
        return taskRepository.findAll(pageable);
    }

    public Page<Task> getSortedAndFilteredTasks(TaskType type, SortType sort, Pageable pageable) {
        if (type == null) {
            return getSortedTasks(sort, pageable);
        }
        if (sort == null) {
            return getFilteredTasks(type, pageable);
        }
        return taskRepository.getFilteredTasks(
                type.getTaskClass(), pageable
        );
    }

    public Task getTaskById(int id) {
        return taskRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Page<Task> getTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public List<Comment> findCommentsByTaskId(Task task) {
        return taskRepository.findCommentsByTaskId(task.getId());
    }
}
