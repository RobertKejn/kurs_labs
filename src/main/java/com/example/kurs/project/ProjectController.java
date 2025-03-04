package com.example.kurs.project;

import org.apache.coyote.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class ProjectController {

    private final ProjectService projectService;
    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }
    @PostMapping("/projects")
    private ResponseEntity<Project> createNewProject(@RequestBody ProjectDTO projectDTO){
        System.out.println(projectDTO);
        Optional<Project> project = projectService.createNewProject(projectDTO);
        if(project.isPresent()) return ResponseEntity.status(201).body(project.get());
        return ResponseEntity.status(400).body(null);
    }

    @PutMapping("/projects/{projectId}")
    private ResponseEntity<Project> updateProject(
            @PathVariable("projectId") Long id,
            @RequestBody ProjectDTO projectDTO){
        Optional<Project> project = projectService.updateProject(id, projectDTO);
        if(project.isPresent()) return ResponseEntity.status(201).body(project.get());
        return ResponseEntity.status(400).body(null);
    }

    @DeleteMapping("/projects/{projectId}")
    private ResponseEntity<Project> deleteProject(
            @PathVariable("projectId") Long id){
        projectService.deleteProject(id);
        return ResponseEntity.status(204).body(null);
    }

    @GetMapping("/projects/{projectId}")
    private ResponseEntity<Project> getProject(
            @PathVariable("projectId") Long id){
        Optional<Project> project = projectService.getProject(id);
        if(project.isPresent()) return ResponseEntity.status(201).body(project.get());
        return ResponseEntity.status(404).body(null);
    }

    @GetMapping("/projects")
    private ResponseEntity<List<Project>> getProjectsByDate(
            @RequestParam("start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start_date,
            @RequestParam("end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end_date){
        List<Project> projects = projectService.getProjectsBetweenDated(start_date, end_date);
        return ResponseEntity.status(200).body(projects);
    }

    @GetMapping("/projects/all")
    private ResponseEntity<List<Project>> getAllProjects(){
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.status(200).body(projects);
    }



}
