package org.might.projman.services.Impl;

import org.might.projman.dba.model.Project;
import org.might.projman.dba.model.ProjectRole;
import org.might.projman.dba.repositories.ProjectRepository;
import org.might.projman.services.ProjectRoleService;
import org.might.projman.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private ProjectRoleService projectRoleService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectRoleService projectRoleService) {
        this.projectRepository = projectRepository;
        this.projectRoleService = projectRoleService;
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.getOne(id);
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
        Iterator<ProjectRole> iterator = projectRoles.iterator();
        while (iterator.hasNext()) {
            projectRoleService.deleteProjectRole(iterator.next());
        }

        projectRepository.delete(project);
    }

    @Override
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

}
