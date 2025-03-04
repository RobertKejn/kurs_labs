package com.example.kurs.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectRepositoryJDBC projectRepositoryJDBC;

    @Autowired
    private ProjectRepositoryNPJDBC projectRepositoryNPJDBC;

    public Optional<Project> createNewProject(ProjectDTO projectDTO) {
        if (checkProjectDTO(projectDTO)) return Optional.empty();
//        Project project = new Project(projectDTO.name, projectDTO.description, projectDTO.start_date, projectDTO.finish_date);
//        projectRepository.save(project);
//        return Optional.of(project);
        return projectRepositoryNPJDBC.createProject(projectDTO);
    }

    public Optional<Project> updateProject(Long id, ProjectDTO projectDTO) {
        if (checkProjectDTO(projectDTO)) return Optional.empty();
//        Optional oldProject = projectRepository.findById(id);
//        if (oldProject.isPresent()) {
//            Project project = (Project) oldProject.get();
//            project.setName(projectDTO.name);
//            project.setDescription(projectDTO.description);
//            project.setStart_date(projectDTO.start_date);
//            project.setFinish_date(projectDTO.finish_date);
//            projectRepository.save(project);
//            return Optional.of(project);
//        } else return Optional.empty();
        return projectRepositoryNPJDBC.updateProject(id, projectDTO);
    }

    private boolean checkProjectDTO(ProjectDTO projectDTO) {
        if (projectDTO.name == null ||
                projectDTO.description == null ||
                projectDTO.start_date == null ||
                projectDTO.finish_date == null ||
                projectDTO.finish_date.before(projectDTO.start_date)) return true;
        return false;
    }

    public void deleteProject(Long id) {
//        projectRepository.deleteById(id);
        projectRepositoryNPJDBC.deleteProjectById(id);
    }

//    public Optional<Project> getProject(Long id) {
//        return projectRepository.findById(id);
//    }

//    public Optional<Project> getProject(Long id) {
//        List l = projectRepositoryJDBC.findProjectByID(id);
//        if(l.size() > 0) return Optional.of((Project) l.get(0));
//        else return Optional.empty();
//    }

    public Optional<Project> getProject(Long id) {
        return projectRepositoryNPJDBC.findProjectByID(id);
    }

    public List<Project> getProjectsBetweenDated(Date startDate, Date endDate) {
        //return projectRepository.findAllBetweenDates(startDate, endDate);
//        return projectRepositoryJDBC.findProjectsBetweenDates(startDate, endDate);
        return projectRepositoryNPJDBC.findProjectsBetweenDates(startDate, endDate);

    }

    public List<Project> getAllProjects() {
        //return projectRepository.findAll();
        return projectRepositoryNPJDBC.findAllProjects();
    }
}
