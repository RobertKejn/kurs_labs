package com.example.kurs.project;

import com.example.kurs.task.TaskRepositoryNPJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepositoryNPJDBC {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private TaskRepositoryNPJDBC taskRepositoryNPJDBC;

    public Optional<Project> findProjectByID(Long id) {
        String sql = "SELECT * FROM Project WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        List<Project> projects = namedParameterJdbcTemplate.query(sql, params, new ProjectRepositoryNPJDBC.ProjectRowMapper());
        if (projects.size() == 0) return Optional.empty();
        return Optional.of(projects.get(0));
    }

    public List<Project> findProjectsBetweenDates(Date startDate, Date endDate) {
        String sql = "SELECT * FROM Project WHERE start_date >= :startDate AND finish_date <= :endDate";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("startDate", startDate)
                .addValue("endDate", endDate);
        List<Project> projects = namedParameterJdbcTemplate.query(sql, params, new ProjectRepositoryNPJDBC.ProjectRowMapper());
        for (int i = 0; i < projects.size(); i++) {
            projects.get(i).setTasks(taskRepositoryNPJDBC.getTasksByProjectId(projects.get(i).getId()));
        }
        return projects;
    }

    public void deleteProjectById(Long id) {
        String sql = "DELETE FROM Project WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        namedParameterJdbcTemplate.query(sql, params, new ProjectRowMapper());
    }

    public Optional<Project> createProject(ProjectDTO projectDTO) {
        String sql = "INSERT INTO Project(name, description, start_date, finish_date)" +
                "VALUES(:name, :description, :startDate, :endDate)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", projectDTO.name)
                .addValue("description", projectDTO.description)
                .addValue("startDate", projectDTO.start_date)
                .addValue("endDate", projectDTO.finish_date);
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params, generatedKeyHolder, new String[]{"id"});
        Project project = new Project(
                generatedKeyHolder.getKey().longValue(),
                projectDTO.name,
                projectDTO.description,
                projectDTO.start_date,
                projectDTO.finish_date);
        return Optional.of(project);
    }

    public Optional<Project> updateProject(Long id, ProjectDTO projectDTO) {
        String sql = "UPDATE project SET name = :name, description = :description, start_date = :startDate, finish_date = :endDate " +
                "WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("name", projectDTO.name)
                .addValue("description", projectDTO.description)
                .addValue("startDate", projectDTO.start_date)
                .addValue("endDate", projectDTO.finish_date);
        int res = namedParameterJdbcTemplate.update(sql, params);
        if(res == 0) return Optional.empty();
        return Optional.of(new Project(
                id,
                projectDTO.name,
                projectDTO.description,
                projectDTO.start_date,
                projectDTO.finish_date
        ));
    }

    public List<Project> findAllProjects(){
        String sql = "SELECT * FROM project";
        MapSqlParameterSource params = new MapSqlParameterSource();
        List<Project> projects = namedParameterJdbcTemplate.query(sql, params, new ProjectRowMapper());
        for(int i = 0; i < projects.size(); i++){
            projects.get(i).setTasks(taskRepositoryNPJDBC.getTasksByProjectId(projects.get(i).getId()));
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
