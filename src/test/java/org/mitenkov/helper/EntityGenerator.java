package org.mitenkov.helper;

import lombok.RequiredArgsConstructor;
import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.dto.UserDto;
import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Feature;
import org.mitenkov.entity.Task;
import org.mitenkov.enums.Priority;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EntityGenerator {

    private final TaskClient taskClient;
    private final TaskConverter taskConverter;
    private final UserClient userClient;

    public List<Task> generateTasks() {
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

        Feature feature3 = Feature.builder()
                .title("TestFeature3")
                .priority(Priority.HIGH)
                .deadline(LocalDate.now().plusDays(20))
                .build();

        Bug bug1 = Bug.builder()
                .deadline(LocalDate.now())
                .priority(Priority.MEDIUM)
                .version("2.1.2")
                .title("TestBug1")
                .build();

        Bug bug2 = Bug.builder()
                .deadline(LocalDate.now())
                .priority(Priority.LOW)
                .version("2.0.0")
                .title("TestBug2")
                .build();

        Bug bug3 = Bug.builder()
                .deadline(LocalDate.now().plusDays(2))
                .priority(Priority.MEDIUM)
                .version("1.15.2")
                .title("TestBug3")
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

    public void generateTasksAndSave() {
        List<Task> tasks = generateTasks();
        for (Task task : tasks) {
            taskClient.create(taskConverter.toAddRequest(task));
        }
    }

    public Map<UserDto, String> generateUsersAndSave() throws Exception {
        UserAddRequest userAddRequest1 = UserAddRequest.builder()
                .username("TestUser1")
                .password("TestPassword1")
                .build();

        UserAddRequest userAddRequest2 = UserAddRequest.builder()
                .username("TestUser2")
                .password("TestPassword2")
                .email("TestEmail2")
                .build();


        UserAddRequest userAddRequest3 = UserAddRequest.builder()
                .username("TestUser3")
                .password("TestPassword3")
                .email("TestEmail3")
                .build();

        Map<UserDto, String> usersAndPasswords = new HashMap<>();

        usersAndPasswords.put(userClient.create(userAddRequest1), userAddRequest1.password());
        usersAndPasswords.put(userClient.create(userAddRequest2), userAddRequest2.password());
        usersAndPasswords.put(userClient.create(userAddRequest3), userAddRequest3.password());

        return usersAndPasswords;
    }

}
