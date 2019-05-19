package org.might.projman.services.Impl;

import org.might.projman.dba.model.ProjectRole;
import org.might.projman.dba.repositories.ProjectRoleRepository;
import org.might.projman.services.ProjectRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectRoleServiceImpl implements ProjectRoleService {

    private ProjectRoleRepository projectRoleRepository;

    @Autowired
    public ProjectRoleServiceImpl(ProjectRoleRepository projectRoleRepository) {
        this.projectRoleRepository = projectRoleRepository;
    }

    @Override
    public ProjectRole getProjectRoleById(Long id) {
        return projectRoleRepository.getOne(id);
    }

    @Override
    public void saveProjectRole(ProjectRole role) {
        projectRoleRepository.save(role);
    }

    @Override
    public void deleteProjectRole(ProjectRole role) {
        projectRoleRepository.delete(role);
    }

    @Override
    public List<ProjectRole> getAll() {
        return projectRoleRepository.findAll();
    }
}
