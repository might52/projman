package org.might.projman.services;

import org.might.projman.dba.model.Project;

import java.util.List;

public interface ProjectService {
    Project getProjectById(Long id);
    void saveProject(Project project);
    void deleteProject(Project project);
    List<Project> getAll();
}
