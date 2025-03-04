package com.example.kurs.project;

import java.time.LocalDate;
import java.util.Date;

public class ProjectDTO {
    public String name;
    public String description;
    public Date start_date;
    public Date finish_date;

    @Override
    public String toString() {
        return "ProjectDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", start_date=" + start_date +
                ", finish_date=" + finish_date +
                '}';
    }
}
