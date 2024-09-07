package org.mitenkov.controller.converter;

import lombok.RequiredArgsConstructor;
import org.mitenkov.dto.TaskDto;
import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Feature;
import org.mitenkov.entity.Task;
import org.mitenkov.enums.TaskType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskDtoConverter {

    public TaskDto createDto(Task task) {
        TaskType type;
        String version = "";

        if (task.getClass().equals(Bug.class)) {
            type = TaskType.BUG;
            version = ((Bug) task).getVersion();
        } else if (task.getClass().equals(Feature.class)) {
            type = TaskType.FEATURE;
        } else {
            throw new IllegalArgumentException("Unsupported task type: " + task.getClass());
        }
        return TaskDto.builder()
                .id(0)
                .title(task.getTitle())
                .deadline(task.getDeadline())
                .priority(task.getPriority())
                .type(type)
                .version(version)
                .build();
    }
}
