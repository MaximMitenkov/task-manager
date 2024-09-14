package org.mitenkov.helper;

import org.mitenkov.entity.Bug;
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
public class TaskGenerator {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> generate() {
        Feature feature1 = Feature.builder()
                .title("TestFeature1")
                .id(1)
                .deadline(LocalDate.now().plusDays(20))
                .priority(Priority.LOW)
                .comments(new ArrayList<>())
                .build();

        Feature feature2 = Feature.builder()
                .title("TestFeature2")
                .id(2)
                .deadline(LocalDate.now().plusDays(20))
                .priority(Priority.LOW)
                .comments(new ArrayList<>())
                .build();

        Feature feature3 = Feature.builder()
                .title("TestFeature3")
                .priority(Priority.HIGH)
                .comments(new ArrayList<>())
                .deadline(LocalDate.now().plusDays(20))
                .build();

        Bug bug1 = Bug.builder()
                .deadline(LocalDate.now())
                .priority(Priority.MEDIUM)
                .version("2.1.2")
                .title("TestBug1")
                .comments(new ArrayList<>())
                .build();

        Bug bug2 = Bug.builder()
                .deadline(LocalDate.now())
                .priority(Priority.LOW)
                .version("2.0.0")
                .title("TestBug2")
                .comments(new ArrayList<>())
                .build();

        Bug bug3 = Bug.builder()
                .deadline(LocalDate.now().plusDays(2))
                .priority(Priority.MEDIUM)
                .version("1.5.2")
                .title("TestBug3")
                .comments(new ArrayList<>())
                .build();

        List<Task> tasks = new ArrayList<>();
        tasks.add(feature1);
        tasks.add(feature2);
        tasks.add(feature3);
        tasks.add(bug1);
        tasks.add(bug2);
        tasks.add(bug3);

        return tasks;
    }

    public List<Task> generateAndSave() {
        List<Task> tasks = generate();
        taskRepository.saveAll(tasks);
        return tasks;
    }

    public List<Task> generateAndSave(int numberOfTasks) {
        List<Task> tasks = generate();
        for (int i = 0; i < numberOfTasks; i++) {
            if (numberOfTasks > tasks.size()) {
                tasks.addAll(generate());
            }
            taskRepository.saveAndFlush(tasks.get(i));
        }
        return tasks;
    }
}
