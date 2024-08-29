package org.mitenkov.repository;

import org.mitenkov.entity.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    default List<Task> getFilteredTasks(Class<? extends Task> type) {
        return getFilteredTasks(type, Sort.unsorted());
    }

    @Query("select t from Task t where t.class = :type")
    List<Task> getFilteredTasks(@Param("type") Class<? extends Task> type, Sort sort);
}
