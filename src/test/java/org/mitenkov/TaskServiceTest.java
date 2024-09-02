package org.mitenkov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mitenkov.controller.ConsoleController;
import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Comment;
import org.mitenkov.entity.Feature;
import org.mitenkov.entity.Task;
import org.mitenkov.enums.Priority;
import org.mitenkov.repository.TaskRepository;
import org.mitenkov.service.TaskService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    ConsoleController consoleController;

    @SpyBean
    TaskRepository taskRepository;

    @BeforeEach
    void reset() {
        Mockito.reset(taskRepository);
    }

    @Test
    void addTaskTest() {
        Feature correctFeature = Feature.builder()
                .deadline(LocalDate.now().plusDays(20))
                .priority(Priority.LOW)
                .title("TestFeature")
                .build();

        Bug correctBug = Bug.builder()
                .version("2.3.4")
                .title("TestBug")
                .priority(Priority.HIGH)
                .deadline(LocalDate.now())
                .build();

        Mockito.doReturn(correctFeature).when(taskRepository).save(correctFeature);
        Mockito.doReturn(correctBug).when(taskRepository).save(correctBug);

        taskService.addTask(correctFeature);
        taskService.addTask(correctBug);

        Mockito.verify(taskRepository, Mockito.times(1)).save(correctFeature);
        Mockito.verify(taskRepository, Mockito.times(1)).save(correctBug);

        Feature incorrectFeature = Feature.builder()
                .deadline(LocalDate.now().plusDays(2))
                .priority(Priority.LOW)
                .title("TestFeature")
                .build();

        Bug incorrectBug = Bug.builder()
                .version("0.3.4")
                .title("TestBug")
                .priority(Priority.HIGH)
                .deadline(LocalDate.now())
                .build();

        assertThrows(InvalidParameterException.class, () -> taskService.addTask(incorrectFeature));
        assertThrows(InvalidParameterException.class, () -> taskService.addTask(incorrectBug));
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
