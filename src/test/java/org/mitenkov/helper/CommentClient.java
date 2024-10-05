package org.mitenkov.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.mitenkov.dto.CommentAddRequest;
import org.mitenkov.dto.CommentDto;
import org.mitenkov.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@RequiredArgsConstructor
public class CommentClient {

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    public CommentDto create(CommentAddRequest commentAddRequest) throws Exception {

        String json = objectMapper.writeValueAsString(commentAddRequest);

        String result = this.mockMvc.perform(post("/comments")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin")
                                .password("12345")
                                .roles(UserRole.Values.ADMIN)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(result, CommentDto.class);

    }

    public Page<CommentDto> getByNickname(String nickname) throws Exception {
        String result = this.mockMvc.perform(get("/comments?nick=" + nickname)
                        .with(user("admin").password("12345")))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(result, new TypeReference<>() {
        });
    }
}
