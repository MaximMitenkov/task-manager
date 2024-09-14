package org.mitenkov;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mitenkov.dto.TaskDto;
import org.mitenkov.entity.Task;
import org.mitenkov.helper.TaskGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerTest extends BaseTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TaskGenerator taskGenerator;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getTaskTest() throws Exception {

        List<Task> tasks = taskGenerator.generateAndSave();

        String responseBody = this.mockMvc.perform(get("/tasks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<TaskDto> result = objectMapper.readValue(responseBody, new TypeReference<>(){});


    }

}
