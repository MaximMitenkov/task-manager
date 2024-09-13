package org.mitenkov.controller.converter;

import lombok.RequiredArgsConstructor;
import org.mitenkov.dto.TaskDto;
import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Task;
import org.mitenkov.enums.TaskType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskDtoConverter {

    public TaskDto toDto(Task task) {
        TaskType type = task.getTaskType();
        String version = null;

        if (type.equals(TaskType.BUG)) {
            version = ((Bug) task).getVersion();
        }
        return new TaskDto(
                task.getId(),
                task.getDeadline(),
                task.getPriority(),
                type,
                task.getTitle(),
                version
        );
    }
}
