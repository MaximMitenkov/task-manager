package org.mitenkov.service;

import lombok.AllArgsConstructor;
import org.mitenkov.entity.Comment;
import org.mitenkov.repository.CommentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void add(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> findAllByNickname(String nickname) {
        return commentRepository.findByAuthor(nickname, Sort.by(Sort.Direction.DESC, "dateTime"));
    }
}
