package org.mitenkov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mitenkov.entity.Task;
import org.mitenkov.repository.TaskRepository;
import org.mitenkov.service.TaskService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Pageable;

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
        assertEquals(taskService.getTasks(Pageable.unpaged()), emptyTaskList);
    }
}
