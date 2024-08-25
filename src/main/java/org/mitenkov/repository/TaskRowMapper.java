package org.mitenkov.repository;

import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Feature;
import org.mitenkov.entity.Task;
import org.mitenkov.enums.Priority;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {

        String type = rs.getString("type");

        switch (type) {
            case ("BUG") -> {
                return Bug.builder()
                        .id(rs.getInt("id"))
                        .title(rs.getString("title"))
                        .priority(Priority.valueOf(rs.getString("priority")))
                        .deadline(LocalDate.parse(rs.getString("deadline")))
                        .version(rs.getString("version"))
                        .build();
            }
            case ("FEATURE") -> {
                return Feature.builder()
                        .id(rs.getInt("id"))
                        .title(rs.getString("title"))
                        .priority(Priority.valueOf(rs.getString("priority")))
                        .deadline(LocalDate.parse(rs.getString("deadline")))
                        .build();
            }
            default -> throw new SQLException("Unknown task found: " + type);
        }
    }
}
