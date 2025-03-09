package com.example.kurs.task;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects/{projectId}")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getAllTasks(@PathVariable("projectId") Long projectId) {
        List<TaskDTO> tasks = taskService.getTasksByProjectId(projectId);
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable("projectId") Long projectId,
                                            @PathVariable("taskId") Long taskId) {
        Optional<TaskDTO> task = taskService.getTaskByTaskIdAndProjectId(projectId, taskId);
        if (task.isPresent()) return ResponseEntity.ok().body(task.get());
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskDTO> createNewTask(@PathVariable("projectId") Long projectId,
                                              @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        Optional<TaskDTO> task = taskService.createTaskForProject(projectId, taskRequestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(task.get().id())
                .toUri();
        if (task.isPresent()) return ResponseEntity.created(location).body(task.get());
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<TaskDTO> updateTaskById(@PathVariable("projectId") Long projectId,
                                               @PathVariable("taskId") Long taskId,
                                               @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        Optional<TaskDTO> task = taskService.updateTaskForProject(projectId, taskId, taskRequestDTO);
        if (task.isPresent()) return ResponseEntity.ok().body(task.get());
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity deleteTaskById(@PathVariable("projectId") Long projectId,
                                               @PathVariable("taskId") Long taskId) {
        taskService.deleteTaskById(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping("/tasks")
    public ResponseEntity deleteTaskByCompletion(@PathVariable("projectId") Long projectId) {
        taskService.deleteTaskByCompletion(projectId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
