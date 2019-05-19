package org.might.projman.services;

import org.might.projman.dba.model.ProjectRole;

import java.util.List;

public interface ProjectRoleService {
    ProjectRole getProjectRoleById(Long id);
    void saveProjectRole(ProjectRole role);
    void deleteProjectRole(ProjectRole role);
    List<ProjectRole> getAll();
}
