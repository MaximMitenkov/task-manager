package org.mitenkov.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.dto.UserDto;
import org.mitenkov.dto.UserPasswordUpdateRequest;
import org.mitenkov.dto.UserUpdateRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import static org.mitenkov.helper.AuthTestHolder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@RequiredArgsConstructor
public class UserClient {

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    private final HeaderCreator headerCreator;

    @SneakyThrows
    public UserDto create(UserAddRequest user) {

        String json = objectMapper.writeValueAsString(user);

        String responseBody = this.mockMvc.perform(post("/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(responseBody, new TypeReference<>() {
        });

    }

    public UserDto update(UserUpdateRequest user) throws Exception {
        String json = objectMapper.writeValueAsString(user);

        String responseBody = this.mockMvc.perform(put("/users")
                        .header("Authorization",
                                headerCreator.createBasicAuthHeader(adminUsername, adminPassword))
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(responseBody, new TypeReference<>() {
        });
    }

    public int updateCurrent(UserUpdateRequest user) throws Exception {
        String json = objectMapper.writeValueAsString(user);

        return this.mockMvc.perform(put("/users/current")
                        .header("Authorization",
                                headerCreator.createBasicAuthHeader(currentUser.getUsername(), currentUserPassword))
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getStatus();
    }

    public UserDto getUser(Integer id) throws Exception {
        String response = this.mockMvc.perform(get("/users/" + id)
                        .header("Authorization",
                                headerCreator.createBasicAuthHeader(currentUser.getUsername(), currentUserPassword)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(response, new TypeReference<>() {
        });
    }

    public int blockUser(Integer id) throws Exception {
        return this.mockMvc.perform(put("/users/{id}/block", id)
                        .header("Authorization",
                                headerCreator.createBasicAuthHeader(currentUser.getUsername(), currentUserPassword)))
                .andReturn().getResponse().getStatus();
    }

    public int unblockUser(Integer id) throws Exception {
        return this.mockMvc.perform(put("/users/{id}/unblock", id)
                        .header("Authorization",
                                headerCreator.createBasicAuthHeader(currentUser.getUsername(), currentUserPassword)))
                .andReturn().getResponse().getStatus();
    }

    public int updatePassword(UserPasswordUpdateRequest request) throws Exception {
        String json = objectMapper.writeValueAsString(request);

        return this.mockMvc.perform(put("/users/password")
                        .header("Authorization",
                                headerCreator.createBasicAuthHeader(currentUser.getUsername(), currentUserPassword))
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getStatus();
    }
}
