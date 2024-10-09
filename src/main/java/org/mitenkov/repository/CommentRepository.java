package org.mitenkov.repository;

import org.mitenkov.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("select c from Comment c join fetch c.task where c.createdBy.username = :nickname")
    Page<Comment> findByAuthor(@Param("nickname") String author, Pageable pageable);
}
