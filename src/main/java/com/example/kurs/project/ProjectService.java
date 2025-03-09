package com.example.kurs.project;

import com.example.kurs.task.TaskDTO;
import com.example.kurs.task.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Optional<ProjectDTO> createNewProject(ProjectRequestDTO projectRequestDTO) {
        Project project = new Project(
                projectRequestDTO.name(),
                projectRequestDTO.description(),
                projectRequestDTO.start_date(),
                projectRequestDTO.finish_date()
        );
        projectRepository.save(project);
        ProjectDTO projectDTO = new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStart_date(),
                project.getFinish_date()
        );
        return Optional.of(projectDTO);
    }

    public Optional<ProjectDTO> updateProject(Long id, ProjectRequestDTO projectRequestDTO) {
        Optional<Project> oldProject = projectRepository.findById(id);//
        if (oldProject.isPresent()) {
            Project project = oldProject.get();
            project.setName(projectRequestDTO.name());
            project.setDescription(projectRequestDTO.description());
            project.setStart_date(projectRequestDTO.start_date());
            project.setFinish_date(projectRequestDTO.finish_date());
            projectRepository.save(project);
            ProjectDTO projectDTO = new ProjectDTO(
                    project.getName(),
                    project.getDescription(),
                    project.getStart_date(),
                    project.getFinish_date()
            );
            return Optional.of(projectDTO);
        } else return Optional.empty();
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public Optional<ProjectDTO> getProject(Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (!projectOptional.isPresent()) return Optional.empty();
        Project project = projectOptional.get();
        return Optional.of(new ProjectDTO(
                project.getName(),
                project.getDescription(),
                project.getStart_date(),
                project.getFinish_date(),
                project.getTasks().stream()
                        .map(task -> new TaskDTO(
                                task.getName(),
                                task.getDescription(),
                                task.getFinish_date(),
                                task.isFinished()
                        )).toList()));
    }

    public List<ProjectDTO> getProjectsBetweenDated(Date startDate, Date endDate) {
        List<Project> projects = projectRepository.findAllBetweenDates(startDate, endDate);
        return projectToProjectDTO(projects);
    }

    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projectToProjectDTO(projects);
    }

    public List<ProjectDTO> findProjectsByPhrase(String search) {
        List<Project> projects = projectRepository.findByPhrase(search);
        List<ProjectDTO> projectDTOS = projects.stream()
                .map(project -> new ProjectDTO(
                        project.getName(),
                        project.getDescription(),
                        project.getStart_date(),
                        project.getFinish_date()
                )).toList();
        return projectDTOS;
    }

    public List<Map<String, Integer>> findUnfinishedTasksForProjects() {
        return taskRepository.countUnfinishedTasksPerProject();
    }

    public List<ProjectDTO> findProjectsByPhraseAndDates(String search, Date startDate, Date endDate) {
        return projectToProjectDTO(projectRepository.findAll(ProjectSpecification.searchByPhraseOrDates(search, startDate, endDate)));
    }

    public List<ProjectDTO> projectToProjectDTO(List<Project> projects) {
        return projects.stream()
                .map(project -> new ProjectDTO(
                        project.getId(),
                        project.getName(),
                        project.getDescription(),
                        project.getStart_date(),
                        project.getFinish_date(),
                        project.getTasks().stream()
                                .map(task -> new TaskDTO(
                                        task.getName(),
                                        task.getDescription(),
                                        task.getFinish_date(),
                                        task.isFinished()
                                )).toList()
                )).toList();
    }
}
