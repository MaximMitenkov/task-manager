package org.mitenkov;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mitenkov.dto.CommentAddRequest;
import org.mitenkov.dto.CommentDto;
import org.mitenkov.helper.DBCleaner;
import org.mitenkov.helper.EntityGenerator;
import org.mitenkov.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentControllerTest extends BaseTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CommentService commentService;

    @Autowired
    DBCleaner cleaner;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EntityGenerator entityGenerator;

    @BeforeEach
    public void beforeEach() {
        cleaner.cleanAll();
        entityGenerator.generateTasksAndSave();
    }

    @Test
    void addComment() throws Exception {

        CommentAddRequest comment = new CommentAddRequest(
                "Content",
                "Author1",
                LocalDateTime.now(),
                1
        );

        String json = objectMapper.writeValueAsString(comment);

        String result = this.mockMvc.perform(post("/comments")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        CommentDto resultComment = objectMapper.readValue(result, CommentDto.class);

        assertEquals(comment.content(), resultComment.content());
        assertEquals(comment.author(), resultComment.author());
        assertEquals(comment.dateTime(), resultComment.dateTime());
        assertEquals(comment.taskId(), resultComment.taskId());
    }

    @Test
    void getCommentByNicknameTest() throws Exception {

        CommentAddRequest comment1 = new CommentAddRequest(
                "Content",
                "Author1",
                LocalDateTime.now().truncatedTo(ChronoUnit.MICROS),
                1
        );

        String json1 = objectMapper.writeValueAsString(comment1);

        CommentAddRequest comment2 = new CommentAddRequest(
                "Content",
                "Author2",
                LocalDateTime.now().truncatedTo(ChronoUnit.MICROS),
                1
        );

        String json2 = objectMapper.writeValueAsString(comment2);

        this.mockMvc.perform(post("/comments")
                .content(json1)
                .contentType(MediaType.APPLICATION_JSON));

        this.mockMvc.perform(post("/comments")
                .content(json2)
                .contentType(MediaType.APPLICATION_JSON));

        String result = this.mockMvc.perform(get("/comments?nick=Author1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<CommentDto> resultComment = objectMapper.readValue(result, new TypeReference<>() {
        });


        assertEquals(1, resultComment.size());
        assertEquals(comment1.author(), resultComment.get(0).author());
        assertEquals(comment1.content(), resultComment.get(0).content());
        assertEquals(comment1.dateTime(), resultComment.get(0).dateTime());
        assertEquals(comment1.taskId(), resultComment.get(0).taskId());

    }

}
