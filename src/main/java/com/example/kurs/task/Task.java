package com.example.kurs.task;

import com.example.kurs.project.Project;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Date finish_date;
    @Column(nullable = false)
    private boolean finished = false;
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;

    public Task() {
    }

    public Task(long id, String name, String description, Date finish_date, boolean finished) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.finish_date = finish_date;
        this.finished = finished;
    }
    public Task(long id, String name, String description, Date finish_date, boolean finished, Project project) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.finish_date = finish_date;
        this.finished = finished;
        this.project = project;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", finish_date=" + finish_date +
                ", finished=" + finished +
                ", project=" + project +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(Date finish_date) {
        this.finish_date = finish_date;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
