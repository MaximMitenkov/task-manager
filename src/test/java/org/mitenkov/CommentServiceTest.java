package org.mitenkov;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mitenkov.controller.ConsoleController;
import org.mitenkov.entity.Comment;
import org.mitenkov.entity.Task;
import org.mitenkov.helper.TaskGenerator;
import org.mitenkov.repository.CommentRepository;
import org.mitenkov.service.CommentService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class CommentServiceTest extends BaseTest {
    @Autowired
    CommentService commentService;

    @MockBean
    ConsoleController consoleController;

    @SpyBean
    CommentRepository commentRepository;

    @Autowired
    TaskGenerator taskGenerator;

    @AfterEach
    void reset() {
        commentRepository.deleteAll();
    }

    @Test
    void addCommentTest() {
        Task task = taskGenerator.generateAndSave().get(0);
        Comment testComment = Comment.builder()
                .dateTime(Timestamp.valueOf(LocalDateTime.now()))
                .author("Tester")
                .content("Test")
                .task(task)
                .build();

        commentService.add(testComment);
        Mockito.verify(commentRepository, Mockito.times(1)).save(Mockito.any(Comment.class));
    }

    @Test
    void findAllByNicknameTest() {
        List<Task> tasks = taskGenerator.generateAndSave();

        String tester1 = "Tester1";
        String tester2 = "Tester2";

        Comment comment1 = Comment.builder()
                .task(tasks.get(0))
                .author(tester1)
                .content("Test")
                .dateTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        Comment comment2 = Comment.builder()
                .task(tasks.get(0))
                .author(tester2)
                .dateTime(Timestamp.valueOf(LocalDateTime.now()))
                .content("Test")
                .build();

        Comment comment3 = Comment.builder()
                .task(tasks.get(1))
                .author(tester2)
                .dateTime(Timestamp.valueOf(LocalDateTime.now()))
                .content("Test")
                .build();

        Comment comment4 = Comment.builder()
                .task(tasks.get(0))
                .author(tester1)
                .dateTime(Timestamp.valueOf(LocalDateTime.now()))
                .content("Test")
                .build();

        commentService.add(comment1);
        commentService.add(comment2);
        commentService.add(comment3);
        commentService.add(comment4);

        List<Comment> comments = commentService.findAllByNickname("Tester1");

        int counter = 0;
        for (Comment comment : comments) {
            if (!Objects.equals(comment.getAuthor(), "Tester1")) {
                throw new AssertionError("Wrong author");
            }
            counter++;
        }
        Assertions.assertEquals(counter, 2);
    }
}
