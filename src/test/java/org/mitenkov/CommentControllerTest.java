package org.mitenkov;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mitenkov.dto.CommentAddRequest;
import org.mitenkov.dto.CommentDto;
import org.mitenkov.helper.CommentClient;
import org.mitenkov.helper.DBCleaner;
import org.mitenkov.helper.EntityGenerator;
import org.mitenkov.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    CommentClient commentClient;

    @BeforeEach
    public void beforeEach() {
        cleaner.cleanComments();
        entityGenerator.generateTasksAndSave();
    }

    @Test
    void addComment() throws Exception {

        CommentAddRequest comment = new CommentAddRequest(
                "Content",
                "Author1",
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                1
        );

        CommentDto resultComment = commentClient.create(comment);

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
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                1
        );

        CommentAddRequest comment2 = new CommentAddRequest(
                "Content",
                "Author2",
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                1
        );

        commentClient.create(comment1);
        commentClient.create(comment2);

        String result = this.mockMvc.perform(get("/comments?nick=Author1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<CommentDto> resultComment = objectMapper.readValue(result, new TypeReference<>() {
        });


        assertEquals(resultComment.size(), 1);
        assertEquals(resultComment.get(0).author(), comment1.author());
        assertEquals(resultComment.get(0).content(), comment1.content());
        assertEquals(resultComment.get(0).dateTime(), comment1.dateTime());
        assertEquals(resultComment.get(0).taskId(), comment1.taskId());

    }

}
