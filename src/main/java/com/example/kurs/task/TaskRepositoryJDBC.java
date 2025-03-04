package com.example.kurs.task;

import com.example.kurs.project.Project;
import com.example.kurs.project.ProjectRepositoryJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TaskRepositoryJDBC {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Task> getTasksByProjectId(Long project_id){
        String sql = "SELECT * FROM Task WHERE project_id = ?";
        List l = jdbcTemplate.query(sql, new TaskRowMapper(), project_id);
        return (List<Task>) l;
    }
    class TaskRowMapper implements RowMapper<Task> {

        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Task(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("finish_date"),
                    rs.getBoolean("finished")
            );
        }
    }
}
