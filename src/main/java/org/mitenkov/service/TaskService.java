package org.mitenkov.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mitenkov.Authintication.AuthHolder;
import org.mitenkov.dto.TaskAddRequest;
import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Feature;
import org.mitenkov.entity.Task;
import org.mitenkov.entity.User;
import org.mitenkov.enums.TaskType;
import org.mitenkov.repository.TaskRepository;
import org.mitenkov.repository.UserRepository;
import org.mitenkov.service.validator.TaskValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.NoSuchElementException;

@Service
@Validated
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskValidator taskValidationService;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional
    public Task addTask(@Valid TaskAddRequest request) {
        taskValidationService.validateTitleLength(request.title());

        int id = AuthHolder.getCurrentUser().getId();
        User user = userRepository.getReferenceById(id);

        return switch (request.type()) {
            case BUG -> {
                String version = request.version();
                taskValidationService.validateVersionNumber(version);
                yield taskRepository.save(Bug.builder()
                        .title(request.title())
                        .priority(request.priority())
                        .deadline(request.deadline())
                        .version(request.version())
                        .user(user)
                        .build());
            }
            case FEATURE -> {
                taskValidationService.validateDeadline(request.deadline());
                yield taskRepository.save(Feature.builder()
                        .title(request.title())
                        .priority(request.priority())
                        .deadline(request.deadline())
                        .user(user)
                        .build());
            }
        };
    }

    @Transactional
    public void removeTask(int id) {
        taskRepository.deleteById(id);
    }

    public Page<Task> getFilteredTasks(TaskType type, Pageable pageable) {
        if (type == null) {
            return getTasks(pageable);
        }
        return taskRepository.getFilteredTasks(type.getTaskClass(), pageable);
    }

    public Task getTaskById(int id) {
        return taskRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Page<Task> getTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }
}
