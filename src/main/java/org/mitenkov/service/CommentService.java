package org.mitenkov.service;

import lombok.AllArgsConstructor;
import org.mitenkov.Authintication.AuthHolder;
import org.mitenkov.dto.CommentAddRequest;
import org.mitenkov.entity.Comment;
import org.mitenkov.entity.User;
import org.mitenkov.repository.CommentRepository;
import org.mitenkov.repository.TaskRepository;
import org.mitenkov.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment add(CommentAddRequest request) {
        User user = userRepository.getReferenceById(AuthHolder.getCurrentUser().getId());
        return commentRepository.save(Comment.builder()
                .dateTime(request.dateTime())
                .content(request.content())
                .task(taskRepository.findById(request.taskId()).orElseThrow())
                .createdBy(user)
                .build());
    }

    public Page<Comment> findAllByNickname(String nickname, Pageable pageable) {
        return commentRepository.findByAuthor(nickname, pageable);
    }
}
