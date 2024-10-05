package org.mitenkov;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mitenkov.Authintication.AuthHolder;
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
        cleaner.cleanAll();
        entityGenerator.generateTasksAndSave();
    }

    @Test
    void addComment() throws Exception {

        CommentAddRequest comment = CommentAddRequest.builder()
                .content("Content")
                .dateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
                .taskId(1)
                .build();

        CommentDto resultComment = commentClient.create(comment);

        assertEquals(comment.content(), resultComment.content());
        assertEquals(AuthHolder.getCurrentUser().getUser().getId(), resultComment.authorId());
        assertEquals(comment.dateTime(), resultComment.dateTime());
        assertEquals(comment.taskId(), resultComment.taskId());
    }

    @Test
    void getCommentByNicknameTest() throws Exception {

        CommentAddRequest comment1 = new CommentAddRequest(
                "Content",
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                1
        );

        CommentAddRequest comment2 = new CommentAddRequest(
                "Content",
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                1
        );

        commentClient.create(comment1);
        commentClient.create(comment2);

        List<CommentDto> resultComment = commentClient.getByNickname("Author1").stream().toList();

        assertEquals(resultComment.size(), 1);
        assertEquals(resultComment.get(0).content(), comment1.content());
        assertEquals(resultComment.get(0).dateTime(), comment1.dateTime());
        assertEquals(resultComment.get(0).taskId(), comment1.taskId());

        resultComment = commentClient.getByNickname("Author2").stream().toList();

        assertEquals(resultComment.size(), 1);
        assertEquals(resultComment.get(0).content(), comment2.content());
        assertEquals(resultComment.get(0).dateTime(), comment2.dateTime());
        assertEquals(resultComment.get(0).taskId(), comment2.taskId());

        resultComment = commentClient.getByNickname("Wrong Author").stream().toList();

        assertEquals(resultComment.size(), 0);

    }

}
