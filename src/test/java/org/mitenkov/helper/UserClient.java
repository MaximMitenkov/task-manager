package org.mitenkov.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.dto.UserDto;
import org.mitenkov.dto.UserUpdateRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@RequiredArgsConstructor
public class UserClient {

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    public UserDto create(UserAddRequest user) throws Exception {

        String json = objectMapper.writeValueAsString(user);

        String responseBody = this.mockMvc.perform(post("/users/reg")
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
                        .with(user("admin").password("admin"))
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(responseBody, new TypeReference<>() {});
    }

    public UserDto getUser(Integer id, String username, String password) throws Exception {
        String response = this.mockMvc.perform(get("/users/" + id).with(user(username).password(password)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(response, new TypeReference<>() {
        });
    }

}
