package org.mitenkov;

import org.junit.jupiter.api.Test;
import org.mitenkov.controller.TaskController;
import org.mitenkov.controller.converter.TaskDtoConverter;
import org.mitenkov.dto.TaskDto;
import org.mitenkov.entity.Task;
import org.mitenkov.enums.Priority;
import org.mitenkov.enums.TaskType;
import org.mitenkov.helper.TaskGenerator;
import org.mitenkov.service.TaskService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TaskService taskService;

    @MockBean
    TaskDtoConverter taskDtoConverter;

    TaskGenerator taskGenerator = new TaskGenerator();

    @Test
    void getTaskTest() throws Exception {
        ArrayList<Task> tasks = new ArrayList<>(taskGenerator.generate());
        Mockito.doReturn(tasks).when(taskService).getTasks();
        Mockito.doReturn(
                new TaskDto(0, LocalDate.now(), Priority.LOW, TaskType.FEATURE, "text", "1.1.1"))
                .when(taskDtoConverter).toDto(any(Task.class));

        this.mockMvc.perform(get("/tasks")).andDo(print()).andExpect(status().isOk());

    }

}
