package org.mitenkov;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mitenkov.dto.CommentAddRequest;
import org.mitenkov.entity.Comment;
import org.mitenkov.helper.CommentConverter;
import org.mitenkov.helper.DBCleaner;
import org.mitenkov.helper.EntityGenerator;
import org.mitenkov.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentServiceTest extends BaseTest {
    @Autowired
    CommentService commentService;

    @Autowired
    EntityGenerator entityGenerator;

    @Autowired
    DBCleaner dbCleaner;

    @Autowired
    MockMvc mockMvc;

    CommentConverter commentConverter = new CommentConverter();

    @BeforeEach
    public void beforeEach() {
        entityGenerator.generateTasksAndSave();
    }

    @AfterEach
    void afterEach() {
        dbCleaner.cleanAll();
    }

    @Test
    public void addCommentTest() {

        CommentAddRequest commentAddRequest = CommentAddRequest.builder()
                .taskId(1)
                .dateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
                .content("Content")
                .build();

        Comment comment = commentService.add(commentAddRequest);

        assertEquals(commentAddRequest, commentConverter.createAddRequest(comment));

    }



}
