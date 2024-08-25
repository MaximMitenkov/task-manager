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

        if (task instanceof Bug bug) {
            jdbcTemplate.update("insert into task (type, title, priority, deadline, version) " +
                            "values ('BUG',?,?,?,?)",
                    task.getTitle(),
                    task.getPriority().toString(),
                    task.getDeadline(),
                    bug.getVersion());
        }

        if (task instanceof Feature) {
            jdbcTemplate.update("insert into task (type, title, priority, deadline) " +
                    "values ('FEATURE',?,?,?)",
                    task.getTitle(),
                    task.getPriority().toString(),
                    task.getDeadline());
        }
    }

    public void removeTask(Task task) {
        jdbcTemplate.execute("delete from task where id = " + task.getId());
    }

    public ArrayList<Task> getFilteredTasks(FilterType type) {
        return new ArrayList<>(jdbcTemplate.query("select * from task where type = '" +
                type.toString().toUpperCase() + "'", taskRowMapper));
    }

    public ArrayList<Task> getSortedTasks(SortType type) {
        return new ArrayList<>(jdbcTemplate.query("select * from task order by " +
                type.toString().toLowerCase() + " DESC", taskRowMapper));
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(jdbcTemplate.query("select * from Task", taskRowMapper));
    }

}
