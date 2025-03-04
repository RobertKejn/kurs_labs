package com.example.kurs.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p WHERE p.start_date >= :startDate AND p.finish_date <= :endDate")
    List<Project> findAllBetweenDates(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);
}
