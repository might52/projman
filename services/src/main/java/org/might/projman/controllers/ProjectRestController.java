package org.might.projman.controllers;


import org.might.projman.controllers.annotations.Auth;
import org.might.projman.controllers.dtos.ProjectDTOResponse;
import org.might.projman.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Auth
public class ProjectRestController {
    
    private ProjectService projectService;
    
    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value = "/projects")
    @ResponseBody
    public List<ProjectDTOResponse> projects(@RequestParam(value = "project_name", defaultValue = "") String projectName) {
        return projectService.getAll().stream()
                .map(project -> project.getName().toUpperCase().matches("(.*)" + projectName.toUpperCase() + "(.*)") ?
                        ProjectDTOResponse.createFromEntity(project) : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
