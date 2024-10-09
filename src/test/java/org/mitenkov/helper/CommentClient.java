package org.mitenkov.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.mitenkov.dto.CommentAddRequest;
import org.mitenkov.dto.CommentDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mitenkov.helper.AuthTestHolder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@RequiredArgsConstructor
public class CommentClient {

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    private final HeaderCreator headerCreator;

    public CommentDto create(CommentAddRequest commentAddRequest) throws Exception {

        String json = objectMapper.writeValueAsString(commentAddRequest);


        String result = this.mockMvc.perform(post("/comments")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,
                                headerCreator.createBasicAuthHeader(currentUser.getUsername(), currentUserPassword)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(result, CommentDto.class);

    }

    public List<CommentDto> getByNickname(String nickname) throws Exception {
        String result = this.mockMvc.perform(get("/comments?nick=" + nickname)
                        .header(HttpHeaders.AUTHORIZATION,
                                headerCreator.createBasicAuthHeader(adminUsername, adminPassword)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(result, new TypeReference<>() {
        });
    }
}
