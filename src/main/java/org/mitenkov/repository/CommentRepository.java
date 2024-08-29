package org.mitenkov.repository;

import org.mitenkov.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findById(int id);
}
