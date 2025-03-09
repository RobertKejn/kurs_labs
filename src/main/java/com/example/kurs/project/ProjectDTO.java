package com.example.kurs.project;

import com.example.kurs.task.TaskDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

public record ProjectDTO(Long id, String name, String description, Date start_date, Date finish_date, List<TaskDTO> tasks) {
    public ProjectDTO(Long id, String name, String description, Date start_date, Date finish_date) {
        this(id, name, description, start_date, finish_date, null);
    }
    public ProjectDTO(String name, String description, Date start_date, Date finish_date, List<TaskDTO> tasks) {
        this(null, name, description, start_date, finish_date, tasks);
    }

    public ProjectDTO(String name, String description, Date start_date, Date finish_date) {
        this(null, name, description, start_date, finish_date, null);
    }
}
