package org.might.projman.services.Impl;

import org.might.projman.dba.model.Project;
import org.might.projman.dba.model.ProjectRole;
import org.might.projman.dba.model.Task;
import org.might.projman.dba.repositories.ProjectRepository;
import org.might.projman.services.ProjectRoleService;
import org.might.projman.services.ProjectService;
import org.might.projman.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private ProjectRoleService projectRoleService;
    private TaskService taskService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              ProjectRoleService projectRoleService,
                              TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectRoleService = projectRoleService;
        this.taskService = taskService;
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).get();
    }

    @Override
    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    @Override
    public void deleteProject(Project project) {
        List<ProjectRole> projectRoles = projectRoleService
                .getAll()
                .stream()
                .filter(projectRole -> projectRole.getProjectId().equals(project))
                .collect(Collectors.toList());
        Iterator<ProjectRole> projectRoleIterator = projectRoles.iterator();
        while (projectRoleIterator.hasNext()) {
            projectRoleService.deleteProjectRole(projectRoleIterator.next());
        }

        List<Task> tasks = taskService
                .getAll()
                .stream()
                .filter(task -> task.getProjectId().equals(project))
                .collect(Collectors.toList());
        Iterator<Task> taskIterator = tasks.iterator();
        while (taskIterator.hasNext()) {
            taskService.deleteTask(taskIterator.next());
        }

        projectRepository.delete(project);
    }

    @Override
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

}
