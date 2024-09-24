package org.mitenkov.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mitenkov.dto.CommentAddRequest;
import org.mitenkov.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class CommentClient {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    public CommentDto create(CommentAddRequest commentAddRequest) throws Exception {

        String json = objectMapper.writeValueAsString(commentAddRequest);

        String result = this.mockMvc.perform(post("/comments")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(result, CommentDto.class);

    }

    public List<CommentDto> getByNickname(String nickname) throws Exception {
        String result = this.mockMvc.perform(get("/comments?nick=" + nickname))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(result, new TypeReference<>() {
        });
    }
}
