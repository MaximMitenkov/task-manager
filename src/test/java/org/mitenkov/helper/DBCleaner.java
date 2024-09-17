package org.mitenkov.helper;

import org.mitenkov.repository.CommentRepository;
import org.mitenkov.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBCleaner {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    CommentRepository commentRepository;

    public void cleanAll() {
        taskRepository.deleteAll();
        commentRepository.deleteAll();
    }

    public void cleanTasks() {
        taskRepository.deleteAll();
    }

    public void cleanComments() {
        commentRepository.deleteAll();
    }

}
