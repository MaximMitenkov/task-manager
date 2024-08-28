package org.mitenkov.repository;

import org.mitenkov.entity.Task;
import org.mitenkov.enums.FilterType;
import org.mitenkov.enums.SortType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("select t from Task t where t.class = :type")
    List<Task> getFilteredTasks(@Param("type") FilterType type);

    @Query("select t from Task t order by :sort desc")
    List<Task> getSortedTasks(@Param("sort") SortType type);

    @Query("select t from Task t where t.class = :type order by :sort desc")
    List<Task> getSortedAndFilteredTasks(@Param("type") FilterType type,
                                         @Param("sort") SortType sortType);
}
