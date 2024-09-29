package org.mitenkov;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mitenkov.dto.TaskAddRequest;
import org.mitenkov.dto.TaskDto;
import org.mitenkov.entity.Task;
import org.mitenkov.enums.Priority;
import org.mitenkov.enums.TaskType;
import org.mitenkov.helper.DBCleaner;
import org.mitenkov.helper.EntityGenerator;
import org.mitenkov.helper.TaskClient;
import org.mitenkov.helper.TaskConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EntityGenerator entityGenerator;

    @Autowired
    TaskClient taskClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DBCleaner dbCleaner;

    @BeforeEach
    public void beforeEach() {
        dbCleaner.cleanAll();
    }

    @Test
    void addTaskTest() throws Exception {

        TaskAddRequest taskAddRequest = new TaskAddRequest(
                "Test",
                "2.1.1",
                LocalDate.now().plusDays(30),
                Priority.LOW,
                TaskType.BUG
        );

        TaskDto result = taskClient.create(taskAddRequest);

        assertEquals(result.title(), taskAddRequest.title());
        assertEquals(result.version(), taskAddRequest.version());
        assertEquals(result.deadline(), taskAddRequest.deadline());
        assertEquals(result.priority(), taskAddRequest.priority());
        assertEquals(result.taskType(), taskAddRequest.type());

        TaskDto dto = taskClient.getById(result.id());

        assertEquals(dto, result);

    }

    @Test
    void deleteTaskTest() throws Exception {

        TaskAddRequest taskAddRequest = new TaskAddRequest(
                "Test",
                "2.1.1",
                LocalDate.now().plusDays(30),
                Priority.LOW,
                TaskType.BUG
        );

        TaskDto result = taskClient.create(taskAddRequest);

        taskClient.deleteById(result.id());

        this.mockMvc.perform(get("/tasks/" + result.id()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTaskTest() throws Exception {

        TaskConverter converter = new TaskConverter();
        List<Task> tasks = entityGenerator.generateTasks();
        List<TaskAddRequest> tasksToAdd = tasks.stream()
                .map(converter::toAddRequest)
                .toList();

        for (TaskAddRequest t : tasksToAdd) {
            taskClient.create(t);
        }

        Page<TaskAddRequest> assertTaskDidNotChange = taskClient.getAll().map(converter::toAddRequest);

        assertEquals(getSet(tasksToAdd), getSet(assertTaskDidNotChange.stream().toList()));

    }
}
