package org.mitenkov.repository;

import org.mitenkov.entity.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("select t from Task t where t.class = :type")
    List<Task> getFilteredTasks(@Param("type") Class<?> type);

    @Query("select t from Task t where t.class = :type")
    List<Task> getFilteredTasks(@Param("type") Class<?> type, Sort sort);

}
