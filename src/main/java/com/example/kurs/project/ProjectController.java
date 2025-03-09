package com.example.kurs.project;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/projects")
    private ResponseEntity<ProjectDTO> createNewProject(@Valid @RequestBody ProjectRequestDTO projectRequestDTO) {
        System.out.println(projectRequestDTO);
        Optional<ProjectDTO> project = projectService.createNewProject(projectRequestDTO);
        if (project.isPresent()) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(project.get().id())
                    .toUri();
            return ResponseEntity.created(location).body(project.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/projects/{projectId}")
    private ResponseEntity<ProjectDTO> updateProject(
            @PathVariable("projectId") Long id,
            @Valid @RequestBody ProjectRequestDTO projectRequestDTO) {
        Optional<ProjectDTO> project = projectService.updateProject(id, projectRequestDTO);
        if (project.isPresent()) return ResponseEntity.status(HttpStatus.ACCEPTED).body(project.get());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/projects/{projectId}")
    private ResponseEntity<Project> deleteProject(
            @PathVariable("projectId") Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/projects/{projectId}")
    private ResponseEntity<ProjectDTO> getProject(
            @PathVariable("projectId") Long id) {
        Optional<ProjectDTO> project = projectService.getProject(id);
        if (project.isPresent()) return ResponseEntity.status(HttpStatus.ACCEPTED).body(project.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/projects")
    private ResponseEntity<List<ProjectDTO>> getProjectsByDate(
            @RequestParam(value = "start_date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start_date,
            @RequestParam(value = "end_date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date end_date,
            @RequestParam(value = "search", required = false) String search) {
        List<ProjectDTO> projects = projectService.findProjectsByPhraseAndDates(search, start_date, end_date);
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/projects/all")
    private ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok().body(projects);
    }


    @GetMapping("/projects/non-finished")
    private ResponseEntity<List<Map<String, Integer>>> getUnfinishedTasksForProjects() {
        List<Map<String, Integer>> list = projectService.findUnfinishedTasksForProjects();
        return ResponseEntity.ok().body(list);
    }


}
