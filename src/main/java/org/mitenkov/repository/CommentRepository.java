package org.mitenkov.repository;

import org.mitenkov.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findById(int id);

    @Query("select c from Comment c where c.author = :nickname")
    List<Comment> findByAuthor(@Param("nickname") String author, Sort sort);
}
