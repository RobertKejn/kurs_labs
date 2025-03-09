package com.example.kurs.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    @Query("SELECT p FROM Project p WHERE p.start_date >= :startDate AND p.finish_date <= :endDate")
    List<Project> findAllBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT p FROM Project p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :phrase, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :phrase, '%'))")
    List<Project> findByPhrase(@Param("phrase") String search);
    @Query("""
    SELECT p FROM Project p
    WHERE (:phrase IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :phrase, '%')) 
        OR LOWER(p.description) LIKE LOWER(CONCAT('%', :phrase, '%')))
    AND (:startDate IS NULL OR p.start_date >= :startDate)
    AND (:endDate IS NULL OR p.finish_date <= :endDate)
    """)
    List<Project> searchProjects(@Param("phrase") String phrase,
                                 @Param("startDate") Date startDate,
                                 @Param("endDate") Date endDate);

}
