package com.example.kurs.task;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND t.id = :taskId")
    Optional<Task> findByProjectIdAndTaskId(@Param("projectId") long projectId, @Param("taskId") long taskId);

    @Query("SELECT t FROM Task t where t.project.id = :projectId")
    List<Task> findAllByProjectId(@Param("projectId") Long projectId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Task t WHERE t.project.id = :projectId AND t.finished = :finished")
    void deleteByCompletion(@Param("projectId") Long projectId, @Param("finished") Boolean finished);

    @Query("SELECT p.id AS projectId, COUNT(t.id) AS unfinishedTasks " +
            "FROM Project p " +
            "LEFT JOIN p.tasks t ON t.finished = false " +
            "GROUP BY p.id")
    List<Map<String, Integer>> countUnfinishedTasksPerProject();
}
