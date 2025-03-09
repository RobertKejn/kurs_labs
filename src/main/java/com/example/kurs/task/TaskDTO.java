package com.example.kurs.task;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TaskDTO(Long id, String name, String description, Date finish_date, Boolean finished) {
    public TaskDTO(String name, String description, Date finish_date, Boolean finished) {
        this(null, name, description, finish_date, finished);
    }
}
