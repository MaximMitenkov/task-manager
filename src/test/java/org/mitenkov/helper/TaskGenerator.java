package org.mitenkov.helper;

import org.mitenkov.BaseTest;
import org.mitenkov.entity.Feature;
import org.mitenkov.entity.Task;
import org.mitenkov.enums.Priority;
import org.mitenkov.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaskGenerator extends BaseTest {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> generate() {
        Feature feature1 = Feature.builder()
                .title("TestFeature1")
                .id(1)
                .deadline(LocalDate.now().plusDays(20))
                .priority(Priority.LOW)
                .build();

        Feature feature2 = Feature.builder()
                .title("TestFeature2")
                .id(2)
                .deadline(LocalDate.now().plusDays(20))
                .priority(Priority.LOW)
                .build();

        List<Task> tasks = new ArrayList<>();
        tasks.add(feature1);
        tasks.add(feature2);

        return tasks;
    }

    public List<Task> generateAndSave() {
        List<Task> tasks = generate();
        taskRepository.saveAll(tasks);
        return tasks;
    }
}
