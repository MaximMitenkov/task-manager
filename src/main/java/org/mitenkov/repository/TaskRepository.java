package org.mitenkov.repository;

import org.mitenkov.entity.Comment;
import org.mitenkov.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("select t from Task t where t.class = :type")
    Page<Task> getFilteredTasks(@Param("type") Class<? extends Task> type, Pageable pageable);

    @Query("select t.comments from Task t where t.id = :id")
    List<Comment> findCommentsByTaskId(@Param("id") int taskId);
}
