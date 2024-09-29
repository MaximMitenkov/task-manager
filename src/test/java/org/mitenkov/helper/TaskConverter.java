package org.mitenkov.helper;

import org.mitenkov.dto.TaskAddRequest;
import org.mitenkov.dto.TaskDto;
import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Feature;
import org.mitenkov.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter {

    public TaskAddRequest toAddRequest(Task task) {
        if (task instanceof Bug bug) {
            return new TaskAddRequest(
                    bug.getTitle(),
                    bug.getVersion(),
                    bug.getDeadline(),
                    bug.getPriority(),
                    bug.getTaskType()
            );
        }

        if (task instanceof Feature feature) {
            return new TaskAddRequest(
                    feature.getTitle(),
                    null,
                    feature.getDeadline(),
                    feature.getPriority(),
                    feature.getTaskType()
            );
        }
        throw new IllegalArgumentException("Unsupported task type: " + task.getClass());
    }

    public TaskAddRequest toAddRequest(TaskDto task) {

        return switch (task.taskType()) {
            case BUG -> new TaskAddRequest(
                    task.title(),
                    task.version(),
                    task.deadline(),
                    task.priority(),
                    task.taskType());
            case FEATURE -> new TaskAddRequest(
                    task.title(),
                    null,
                    task.deadline(),
                    task.priority(),
                    task.taskType());
        };
    }
}
