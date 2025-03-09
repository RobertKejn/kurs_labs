package com.example.kurs.task;

import com.example.kurs.project.ProjectRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;

public record TaskRequestDTO (
    @NotBlank
    String name,
    @NotBlank
    String description,
    @NotNull
    Boolean completed,
    @NotNull
    Date endDate) {
}
