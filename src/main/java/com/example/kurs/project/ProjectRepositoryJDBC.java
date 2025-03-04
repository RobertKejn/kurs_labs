package com.example.kurs.project;

import com.example.kurs.task.Task;
import com.example.kurs.task.TaskRepositoryJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProjectRepositoryJDBC {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TaskRepositoryJDBC taskRepositoryJDBC;

    public Optional<Project> findProjectByID(Long id){
        String sql = "SELECT * FROM Project WHERE id = ?";
        List l = jdbcTemplate.query(sql, new ProjectRowMapper(), id);
        if(l.size() == 0) return Optional.empty();
        Project project = (Project) l.get(0);
        project.setTasks(taskRepositoryJDBC.getTasksByProjectId(project.getId()));
        return Optional.of(project); // надо добавить сборку объекто
    }

    public List<Project> findProjectsBetweenDates(Date startDate, Date endDate){
        String sql = "SELECT * FROM Project WHERE start_date >= ? AND finish_date <= ?";
        List l = jdbcTemplate.query(sql, new ProjectRowMapper(), startDate, endDate);
        List<Project> projects = (List<Project>) l;
        for(int i = 0; i < projects.size(); i++) {
            projects.get(i).setTasks(taskRepositoryJDBC.getTasksByProjectId(projects.get(i).getId()));
        }
        return projects;
    }

    class ProjectRowMapper implements RowMapper<Project> {

        @Override
        public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Project(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("start_date"),
                    rs.getDate("finish_date")
            );
        }
    }
}
