package com.example.kurs.task;

import com.example.kurs.project.Project;
import com.example.kurs.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskRepository taskRepository;

    public Optional<TaskDTO> getTaskByTaskIdAndProjectId(Long projectId, Long taskId) {
        Optional<Task> taskOptional = taskRepository.findByProjectIdAndTaskId(projectId, taskId);
        if (!taskOptional.isPresent()) return Optional.empty();
        Task task = taskOptional.get();
        TaskDTO taskDTO = new TaskDTO(task.getName(), task.getDescription(), task.getFinish_date(), task.isFinished());
        return Optional.of(taskDTO);
    }

    public List<TaskDTO> getTasksByProjectId(Long projectId) {
        List<Task> tasks = taskRepository.findAllByProjectId(projectId);
        List<TaskDTO> taskDTOS = tasks.stream()
                .map(task -> new TaskDTO(
                        task.getId(),
                        task.getName(),
                        task.getDescription(),
                        task.getFinish_date(),
                        task.isFinished()))
                .collect(Collectors.toList());
        return taskDTOS;
    }

    public Optional<TaskDTO> createTaskForProject(Long projectId, TaskRequestDTO taskRequestDTO) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (!project.isPresent()) return Optional.empty();
        else {
            Task task = new Task(
                    taskRequestDTO.name(),
                    taskRequestDTO.description(),
                    taskRequestDTO.endDate(),
                    taskRequestDTO.completed(),
                    project.get());
            taskRepository.save(task);
            TaskDTO taskDTO = new TaskDTO(task.getId(),
                    task.getName(),
                    task.getDescription(),
                    task.getFinish_date(),
                    task.isFinished());
            return Optional.of(taskDTO);
        }
    }

    public Optional<TaskDTO> updateTaskForProject(Long projectId, Long taskId, TaskRequestDTO taskRequestDTO) {
        Optional<Project> project = projectRepository.findById(projectId);
        Optional<Task> task = taskRepository.findByProjectIdAndTaskId(projectId, taskId);
        if (!project.isPresent() || !task.isPresent()) return Optional.empty();
        else {
            Task oldTask = task.get();
            oldTask.setName(taskRequestDTO.name());
            oldTask.setDescription(taskRequestDTO.description());
            oldTask.setFinished(taskRequestDTO.completed());
            oldTask.setFinish_date(taskRequestDTO.endDate());
            taskRepository.save(oldTask);
            TaskDTO taskDTO = new TaskDTO(oldTask.getName(), oldTask.getDescription(), oldTask.getFinish_date(), oldTask.isFinished());
            return Optional.of(taskDTO);
        }
    }

    public void deleteTaskById(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public void deleteTaskByCompletion(Long projectId) {
        taskRepository.deleteByCompletion(projectId, true);
    }


}
