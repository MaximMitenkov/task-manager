package org.mitenkov.controller;

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
@Slf4j
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskDtoConverter taskDtoConverter;

    @GetMapping
    public List<TaskDto> getTasks(
            @RequestParam(value = "sort", required = false) SortType sort,
            @RequestParam(value = "type", required = false) TaskType type
    ) {
        log.info("Get task request for type {} and sort {}", type, sort);
        return taskService.getSortedAndFilteredTasks(type, sort).stream()
                .map(taskDtoConverter::createDto)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTask(@RequestBody TaskAddRequest request) {
        taskService.addTask(request);
    }

    @DeleteMapping
    public void deleteTaskById(@RequestParam(value = "id") int id) {
        taskService.removeTask(id);
    }


}
