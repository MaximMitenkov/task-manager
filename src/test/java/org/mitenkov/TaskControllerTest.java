package org.mitenkov;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mitenkov.dto.TaskAddRequest;
import org.mitenkov.dto.TaskDto;
import org.mitenkov.entity.Task;
import org.mitenkov.enums.Priority;
import org.mitenkov.enums.TaskType;
import org.mitenkov.helper.DBCleaner;
import org.mitenkov.helper.TaskConverter;
import org.mitenkov.helper.TaskGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TaskGenerator taskGenerator;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DBCleaner dbCleaner;

    @BeforeEach
    public void beforeEach() throws Exception {
        dbCleaner.cleanTasks();
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

        String json = objectMapper.writeValueAsString(taskAddRequest);

        String responseBody = this.mockMvc.perform(post("/tasks")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TaskDto result = objectMapper.readValue(responseBody, new TypeReference<>() {
        });

        assertEquals(result.title(), taskAddRequest.title());
        assertEquals(result.version(), taskAddRequest.version());
        assertEquals(result.deadline(), taskAddRequest.deadline());
        assertEquals(result.priority(), taskAddRequest.priority());
        assertEquals(result.taskType(), taskAddRequest.type());

        String addedTaskResponse = this.mockMvc.perform(get("/tasks/" + result.id()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TaskDto dto = objectMapper.readValue(addedTaskResponse, TaskDto.class);

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

        String json = objectMapper.writeValueAsString(taskAddRequest);

        String responseBody = this.mockMvc.perform(post("/tasks")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TaskDto result = objectMapper.readValue(responseBody, new TypeReference<>() {
        });

        this.mockMvc.perform(delete("/tasks/" + result.id()))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/tasks/" + result.id()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTaskTest() throws Exception {

        TaskConverter converter = new TaskConverter();

        List<Task> tasks = taskGenerator.generate();

        List<TaskAddRequest> tasksToAdd = tasks.stream()
                .map(converter::toAddRequest)
                .toList();

        for (TaskAddRequest task : tasksToAdd) {
            this.mockMvc.perform(post("/tasks")
                    .content(objectMapper.writeValueAsString(task))
                    .contentType(MediaType.APPLICATION_JSON));
        }

        String responseBody = this.mockMvc.perform(get("/tasks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<TaskDto> result = objectMapper.readValue(responseBody, new TypeReference<>() {
        });

        List<TaskAddRequest> assertTaskDidNotChange = new ArrayList<>();

        for (TaskDto task : result) {
            assertTaskDidNotChange.add(converter.toAddRequest(task));
        }

        assertEquals(getSet(tasksToAdd), getSet(assertTaskDidNotChange));

    }

}
