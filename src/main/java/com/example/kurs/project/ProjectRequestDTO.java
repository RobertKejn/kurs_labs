package com.example.kurs.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record ProjectRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotNull
        Date start_date,
        @NotNull
        Date finish_date
) {
    public ProjectRequestDTO {
        if (start_date != null && finish_date != null && start_date.after(finish_date)) {
            throw new IllegalArgumentException("Дата начала не может быть позже даты окончания");
        }
    }
}
