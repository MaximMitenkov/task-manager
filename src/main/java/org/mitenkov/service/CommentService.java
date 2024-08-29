package org.mitenkov.service;

import lombok.AllArgsConstructor;
import org.mitenkov.entity.Comment;
import org.mitenkov.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void add(Comment comment) {
        commentRepository.save(comment);
    }

    public Comment getComment(int id) {
        return commentRepository.findById(id);
    }
}
