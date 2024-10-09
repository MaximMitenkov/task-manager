package org.mitenkov.helper;

import lombok.RequiredArgsConstructor;
import org.mitenkov.dto.UserAddRequest;
import org.mitenkov.repository.CommentRepository;
import org.mitenkov.repository.TaskRepository;
import org.mitenkov.repository.UserRepository;
import org.springframework.stereotype.Component;

import static org.mitenkov.helper.AuthTestHolder.adminPassword;
import static org.mitenkov.helper.AuthTestHolder.adminUsername;

@Component
@RequiredArgsConstructor
public class DBCleaner {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final UserClient userClient;

    public void reset() {
        taskRepository.deleteAll();
        commentRepository.deleteAll();
        userRepository.deleteAll();
        userClient.create(new UserAddRequest(adminUsername,null, adminPassword));
    }

    public void cleanAll() {
        commentRepository.deleteAll();
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    public void cleanAllButUsers() {
        commentRepository.deleteAll();
        taskRepository.deleteAll();
    }
}
