package org.mitenkov.service;

import lombok.AllArgsConstructor;
import org.mitenkov.controller.converter.CommentDtoConverter;
import org.mitenkov.dto.CommentAddRequest;
import org.mitenkov.entity.Comment;
import org.mitenkov.repository.CommentRepository;
import org.mitenkov.repository.TaskRepository;
import org.mitenkov.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final CommentDtoConverter commentDtoConverter;
    private final UserRepository userRepository;

    public Comment add(CommentAddRequest request) {
        return commentRepository.save(Comment.builder()
                .dateTime(request.dateTime())
                .content(request.content())
                .task(taskRepository.findById(request.taskId()).orElseThrow())
                .createdBy(userRepository.findUserById(request.authorId()))
                .build());
    }

    public Page<Comment> findAllByNickname(String nickname, Pageable pageable) {
        return commentRepository.findByAuthor(nickname, pageable);
    }

    public Comment findById(int id) {
        return commentRepository.findById(id).orElseThrow();
    }
}
