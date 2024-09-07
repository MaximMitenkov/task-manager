package org.mitenkov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mitenkov.entity.Comment;
import org.mitenkov.entity.Feature;
import org.mitenkov.entity.Task;
import org.mitenkov.enums.Priority;
import org.mitenkov.repository.TaskRepository;
import org.mitenkov.service.TaskService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskServiceTest extends BaseTest{

    @Autowired
    private TaskService taskService;

    @SpyBean
    TaskRepository taskRepository;

    @BeforeEach
    void reset() {
        Mockito.reset(taskRepository);
    }

    @Test
    void getAllTasksTest() {
        ArrayList<Task> emptyTaskList = new ArrayList<>();
        Mockito.doReturn(emptyTaskList).when(taskRepository).findAll();
        assertEquals(taskService.getTasks(), emptyTaskList);
    }

    @Test
    void findCommentsByTaskIdTest() {

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

        ArrayList<Comment> commentList = new ArrayList<>();
        commentList.add(testComment);
        testFeature.setComments(commentList);

        Mockito.doReturn(commentList).when(taskRepository).findCommentsByTaskId(testFeature.getId());

        assertEquals(commentList, taskService.findCommentsByTaskId(testFeature));
    }
}
