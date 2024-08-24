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
import java.util.Objects;

@Component
public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (Objects.equals(rs.getString(2), "Bug")) {
            return Bug.builder()
                    .id(rs.getInt("id"))
                    .title(rs.getString("title"))
                    .priority(Priority.valueOf(rs.getString("priority")))
                    .deadline(LocalDate.parse(rs.getString("deadline")))
                    .version(rs.getString("version"))
                    .build();
        } else if (Objects.equals(rs.getString(2), "Feature")) {
            return Feature.builder()
                    .id(rs.getInt("id"))
                    .title(rs.getString("title"))
                    .priority(Priority.valueOf(rs.getString("priority")))
                    .deadline(LocalDate.parse(rs.getString("deadline")))
                    .build();
        }
        throw new SQLException("Unknown Task");
    }
}
