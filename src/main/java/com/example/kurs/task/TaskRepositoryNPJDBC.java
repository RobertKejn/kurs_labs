package com.example.kurs.task;

import com.example.kurs.project.ProjectRepositoryNPJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TaskRepositoryNPJDBC {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Task> getTasksByProjectId(Long project_id){
        String sql = "SELECT * FROM Task WHERE project_id = :project_id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("project_id", project_id);
        List l =  namedParameterJdbcTemplate.query(sql, params, new TaskRowMapper());
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
