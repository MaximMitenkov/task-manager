package org.mitenkov.repository;

import lombok.RequiredArgsConstructor;
import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Feature;
import org.mitenkov.entity.Task;
import org.mitenkov.enums.FilterType;
import org.mitenkov.enums.SortType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    private final TaskRowMapper taskRowMapper;

    public void addTask(Task task) {

        if (task instanceof Bug) {
            jdbcTemplate.update("insert into task (type, title, priority, deadline, version) " +
                            "values (?,?,?,?,?)",
                    "Bug",
                    task.getTitle(),
                    task.getPriority().toString(),
                    task.getDeadline(),
                    ((Bug) task).getVersion());
        }

        if (task instanceof Feature) {
            jdbcTemplate.update("insert into task (type, title, priority, deadline) " +
                    "values (?,?,?,?)",
                    "Feature",
                    task.getTitle(),
                    task.getPriority().toString(),
                    task.getDeadline());
        }
    }

    public void removeTask(Task task) {
        jdbcTemplate.execute("delete from task where id = " + task.getId());
    }

    public ArrayList<Task> getFilteredTasks(FilterType type) {
        return new ArrayList<>(getTasks().stream().filter(type.getPredicate()).toList());
    }

    public ArrayList<Task> getSortedTasks(SortType type) {
        return new ArrayList<>(getTasks().stream().sorted(type.getComparator()).toList());
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(jdbcTemplate.query("select * from Task", taskRowMapper));
    }

}
