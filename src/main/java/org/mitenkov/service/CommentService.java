package org.mitenkov.service;

import lombok.AllArgsConstructor;
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

    public Comment add(CommentAddRequest request) {
        return commentRepository.save(Comment.builder()
                .dateTime(request.dateTime())
                .author(request.author())
                .content(request.content())
                .task(taskRepository.findById(request.taskId()).orElseThrow())
                .build());
    }

    public List<Comment> findAllByNickname(String nickname) {
        return  commentRepository.findByAuthor(nickname, Sort.by(Sort.Direction.DESC, "dateTime"));
    }

    public Comment findById(Integer id) {
        return commentRepository.findById(id).orElseThrow();
    }
}
