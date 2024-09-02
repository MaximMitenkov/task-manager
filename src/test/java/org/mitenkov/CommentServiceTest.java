package org.mitenkov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mitenkov.controller.ConsoleController;
import org.mitenkov.entity.Comment;
import org.mitenkov.entity.Feature;
import org.mitenkov.enums.Priority;
import org.mitenkov.repository.CommentRepository;
import org.mitenkov.service.CommentService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @MockBean
    ConsoleController consoleController;

    @SpyBean
    CommentRepository commentRepository;

    @BeforeEach
    void reset() {
        Mockito.reset(commentRepository);
    }

    @Test
    void addCommentTest() {
        Feature testFeature = Feature.builder()
                .title("TestFeature")
                .id(1)
                .deadline(LocalDate.now())
                .priority(Priority.LOW)
                .build();

        Comment testComment = Comment.builder()
                .dateTime(Timestamp.valueOf(LocalDateTime.now()))
                .author("Tester")
                .content("Test")
                .task(testFeature)
                .build();

        Mockito.doReturn(testComment).when(commentRepository).save(Mockito.any(Comment.class));

        commentService.add(testComment);
        Mockito.verify(commentRepository, Mockito.times(1)).save(Mockito.any(Comment.class));
    }
}
