package org.mitenkov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mitenkov.controller.converter.TaskDtoConverter;
import org.mitenkov.dto.TaskAddRequest;
import org.mitenkov.dto.TaskDto;
import org.mitenkov.enums.SortType;
import org.mitenkov.enums.TaskType;
import org.mitenkov.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks")
@Slf4j
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskDtoConverter taskDtoConverter;

    @GetMapping
    @Operation(summary = "get tasks", description = "Getting sorted and filtered tasks by type")
    public List<TaskDto> getTasks(
            @RequestParam(value = "sort", required = false) SortType sort,
            @RequestParam(value = "type", required = false) TaskType type
    ) {
        log.info("Get task request for type {} and sort {}", type, sort);
        return taskService.getSortedAndFilteredTasks(type, sort).stream()
                .map(taskDtoConverter::toDto)
                .toList();
    }

    @PostMapping
    @Operation(summary = "add task", description = "Add task. For task type BUG version is required")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTask(@RequestBody TaskAddRequest request) {
        taskService.addTask(request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete task", description = "Delete task with written id")
    public void deleteTaskById(@PathVariable(value = "id") int id) {
        taskService.removeTask(id);
    }

}
