package org.mitenkov.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.mitenkov.dto.TaskAddRequest;
import org.mitenkov.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import static org.mitenkov.helper.AuthTestHolder.adminPassword;
import static org.mitenkov.helper.AuthTestHolder.adminUsername;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@RequiredArgsConstructor
public class TaskClient {

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    private final HeaderCreator headerCreator;

    @SneakyThrows
    public TaskDto create(TaskAddRequest request) {

        String json = objectMapper.writeValueAsString(request);

        String responseBody = this.mockMvc.perform(post("/tasks")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization",
                                headerCreator.createBasicAuthHeader(adminUsername, adminPassword)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(responseBody, new TypeReference<>() {
        });
    }

    public TaskDto getById(int id) throws Exception {

        String addedTaskResponse = this.mockMvc.perform(get("/tasks/" + id)
                        .header("Authorization",
                                headerCreator.createBasicAuthHeader(adminUsername, adminPassword)))
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(addedTaskResponse, TaskDto.class);
    }

    public int getByIdStatus(int id) throws Exception {
        return this.mockMvc.perform(get("/tasks/" + id)
                        .header("Authorization",
                                headerCreator.createBasicAuthHeader(adminUsername, adminPassword)))
                .andReturn().getResponse().getStatus();
    }

    public void deleteById(int id) throws Exception {
        this.mockMvc.perform(delete("/tasks/" + id)
                        .header("Authorization",
                                headerCreator.createBasicAuthHeader(adminUsername, adminPassword)))
                .andExpect(status().isOk());
    }

    public Page<TaskDto> getAll() throws Exception {
        String responseBody = this.mockMvc.perform(get("/tasks")
                        .header("Authorization",
                                headerCreator.createBasicAuthHeader(adminUsername, adminPassword)))
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(responseBody, new TypeReference<>() {
        });
    }
}
