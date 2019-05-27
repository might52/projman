package org.might.projman.controllers;


import org.might.projman.controllers.annotations.Auth;
import org.might.projman.controllers.dtos.ProjectDTOResponse;
import org.might.projman.services.ProjectRoleService;
import org.might.projman.services.ProjectService;
import org.might.projman.services.TaskService;
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
    private TaskService taskService;
    private ProjectRoleService projectRoleService;

    public ProjectRestController(TaskService taskService,
                                 ProjectRoleService projectRoleService,
                                 ProjectService projectService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.projectRoleService = projectRoleService;
    }

    @RequestMapping(value = "/projects")
    @ResponseBody
    public List<ProjectDTOResponse> projects(@RequestParam(value = "project_name", defaultValue = "") String projectName) {
        return projectService.getAll().stream()
                .map(project -> project.getName().toUpperCase().matches("(.*)" + projectName.toUpperCase() + "(.*)") ?
                        ProjectDTOResponse.createFromEntity(project, taskService.getAll(), projectRoleService.getAll()) : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
