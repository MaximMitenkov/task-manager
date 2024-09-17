package org.mitenkov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mitenkov.controller.converter.TaskDtoConverter;
import org.mitenkov.dto.TaskAddRequest;
import org.mitenkov.dto.TaskDto;
import org.mitenkov.enums.SortType;
import org.mitenkov.enums.TaskType;
import org.mitenkov.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Everything worked correct"),
        @ApiResponse(responseCode = "400", description = "Invalid data supplied, bad request"),
        @ApiResponse(responseCode = "404", description = "Object not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
})
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

    @GetMapping("/{id}")
    @Operation(summary = "get task by id", description = "Get task by ID")
    public TaskDto getTaskById(@PathVariable(value = "id") int id) {
        log.info("Get task request for id {}", id);
        return taskDtoConverter.toDto(taskService.getTaskById(id));
    }

    @PostMapping
    @Operation(summary = "add task", description = "Add task. For task type BUG version is required")
    public TaskDto addTask(@RequestBody TaskAddRequest request) {
        return taskDtoConverter.toDto(taskService.addTask(request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete task", description = "Delete task with written id")
    public void deleteTaskById(@PathVariable(value = "id") int id) {
        taskService.removeTask(id);
    }

}
