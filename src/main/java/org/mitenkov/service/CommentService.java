package org.mitenkov.service;

import lombok.AllArgsConstructor;
import org.mitenkov.controller.converter.CommentDtoConverter;
import org.mitenkov.dto.CommentAddRequest;
import org.mitenkov.entity.Comment;
import org.mitenkov.repository.CommentRepository;
import org.mitenkov.repository.TaskRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    public void add(CommentAddRequest request) {
        commentRepository.save(Comment.builder()
                .dateTime(request.getDateTime())
                .author(request.getAuthor())
                .content(request.getContent())
                .task(taskRepository.findById(request.getTaskId()).orElseThrow())
                .build());
    }

    public List<Comment> findAllByNickname(String nickname) {
        return  commentRepository.findByAuthor(nickname, Sort.by(Sort.Direction.DESC, "dateTime"));
    }
}
