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

        if (task instanceof Bug) {
            type = TaskType.BUG;
            version = ((Bug) task).getVersion();
        } else if (task instanceof Feature) {
            type = TaskType.FEATURE;
        } else {
            throw new IllegalArgumentException("Invalid task type: " + task.getTaskClass());
        }

        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .deadline(task.getDeadline())
                .priority(task.getPriority())
                .type(type)
                .version(version)
                .build();
    }
}
