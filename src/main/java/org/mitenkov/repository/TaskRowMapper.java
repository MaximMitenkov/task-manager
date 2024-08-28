package org.mitenkov.repository;

import org.mitenkov.entity.Bug;
import org.mitenkov.entity.Feature;
import org.mitenkov.entity.Task;
import org.mitenkov.enums.Priority;
import org.mitenkov.enums.TaskType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class TaskRowMapper implements RowMapper<Task> {

    private static final Logger log = LoggerFactory.getLogger(TaskRowMapper.class);

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {

        TaskType type = TaskType.valueOf(rs.getString("type"));

        try {
            return switch (type) {
                case BUG -> Bug.builder()
                            .id(rs.getInt("id"))
                            .title(rs.getString("title"))
                            .priority(Priority.valueOf(rs.getString("priority")))
                            .deadline(LocalDate.parse(rs.getString("deadline")))
                            .version(rs.getString("version"))
                            .build();
                case FEATURE -> Feature.builder()
                            .id(rs.getInt("id"))
                            .title(rs.getString("title"))
                            .priority(Priority.valueOf(rs.getString("priority")))
                            .deadline(LocalDate.parse(rs.getString("deadline")))
                            .build();
            };
        } catch (SQLException e) {
            log.error("Unknown type {} provided", type, e);
            throw new SQLException("Unknown type " + type);
        } catch (IllegalArgumentException e) {
            log.error("Unknown type {} provided", type, e);
            throw new RuntimeException(e);
        }
    }
}
