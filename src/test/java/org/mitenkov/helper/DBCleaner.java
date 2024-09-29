package org.mitenkov.helper;

import lombok.RequiredArgsConstructor;
import org.mitenkov.repository.CommentRepository;
import org.mitenkov.repository.TaskRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DBCleaner {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    public void cleanAll() {
        commentRepository.deleteAll();
        taskRepository.deleteAll();
    }
}
