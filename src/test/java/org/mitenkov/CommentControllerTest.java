package org.mitenkov;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mitenkov.dto.CommentAddRequest;
import org.mitenkov.dto.CommentDto;
import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.helper.*;
import org.mitenkov.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mitenkov.helper.AuthTestHolder.*;

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

    @Autowired
    AuthTestHolder authHolder;

    @Autowired
    TaskClient taskClient;

    @Autowired
    UserClient userClient;

    @BeforeEach
    public void beforeEach() {
        cleaner.reset();
        entityGenerator.generateTasksAndSave();
        authHolder.setCurrentUser();
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
        assertEquals(currentUser.getId(), resultComment.authorId());
        assertEquals(comment.dateTime(), resultComment.dateTime());
        assertEquals(comment.taskId(), resultComment.taskId());
    }

    @Test
    void getCommentByNicknameTest() throws Exception {

        userClient.create(UserAddRequest.builder()
                .username(defaultUsername)
                .password(defaultPassword)
                .build());
        int taskId = taskClient.getAll().get(0).id();

        CommentAddRequest comment1 = new CommentAddRequest(
                "Content",
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                taskId
        );

        commentClient.create(comment1);
        authHolder.setCurrentUser(defaultUsername, defaultPassword);

        CommentAddRequest comment2 = new CommentAddRequest(
                "Content",
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                taskId
        );

        commentClient.create(comment2);

        List<CommentDto> resultComment = commentClient.getByNickname(adminUsername);

        assertEquals(1, resultComment.size());
        assertEquals(comment1.content(), resultComment.get(0).content());
        assertEquals(comment1.dateTime(), resultComment.get(0).dateTime());
        assertEquals(comment1.taskId(), resultComment.get(0).taskId());

        resultComment = commentClient.getByNickname(defaultUsername);

        assertEquals(1, resultComment.size());
        assertEquals(comment2.content(), resultComment.get(0).content());
        assertEquals(comment2.dateTime(), resultComment.get(0).dateTime());
        assertEquals(comment2.taskId(), resultComment.get(0).taskId());
    }
}
