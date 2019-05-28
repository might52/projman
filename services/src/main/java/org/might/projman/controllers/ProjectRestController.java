package org.might.projman.controllers;


import org.might.projman.controllers.annotations.Auth;
import org.might.projman.controllers.dtos.ProjectDTOResponse;
import org.might.projman.dba.model.*;
import org.might.projman.model.CreateEditProjectViewModel;
import org.might.projman.services.*;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Auth
public class ProjectRestController {
    
    private ProjectService projectService;
    private TaskService taskService;
    private ProjectRoleService projectRoleService;
    private StatusService statusService;
    private RoleService roleService;
    private UserService userService;

    public ProjectRestController(TaskService taskService,
                                 ProjectRoleService projectRoleService,
                                 ProjectService projectService,
                                 StatusService statusService,
                                 RoleService roleService,
                                 UserService userService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.projectRoleService = projectRoleService;
        this.statusService = statusService;
        this.roleService = roleService;
        this.userService = userService;
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

    @GetMapping(value = "/update_task_time")
    @ResponseBody
    public String logTime(@RequestParam(value = "task_id") long taskId, @RequestParam(value = "time") int time) {
        Task task = taskService.getTaskById(taskId);
        task.setUsedTime(time);
        taskService.saveTask(task);
        return "ok";
    }

    @GetMapping(value = "/update_task_status")
    @ResponseBody
    public String updateStatus(@RequestParam(value = "task_id") long taskId, @RequestParam(value = "status_name") String statusName) {
        Task task = taskService.getTaskById(taskId);
        Optional<Status> optionalStatus = statusService.getAll()
                .stream()
                .filter(status -> status.getName().startsWith(statusName))
                .findFirst();
        if (optionalStatus.isPresent()) {
            task.setStatusId(optionalStatus.get());
            taskService.saveTask(task);
            return "ok";
        } else {
            return "Can not find status by name";
        }
    }

    @GetMapping(value = "/add_user_to_project")
    @ResponseBody
    public String addUserToProject(
            @RequestParam(value = "project_id") long projectId,
            @RequestParam(value = "user_id") long user_id,
            @RequestParam(value = "role_id") long role_id
    ) {
        Project project = projectService.getProjectById(projectId);
        User user = userService.getUserById(user_id);
        Role role = roleService.getRoleById(role_id);

        Optional<ProjectRole> existProjectRole = projectRoleService.getAll()
                .stream()
                .filter(pr -> pr.getProjectId().getId().equals(projectId) && pr.getUserId().getId().equals(user_id))
                .findFirst();
        ProjectRole projectRole;
        if (existProjectRole.isPresent()) {
            projectRole = existProjectRole.get();
            projectRole.setRoleId(role);
        } else {
            projectRole = new ProjectRole();
            projectRole.setRoleId(role);
            projectRole.setProjectId(project);
            projectRole.setUserId(user);
        }
        projectRoleService.saveProjectRole(projectRole);
        return "ok";
    }

    @GetMapping(value = "/update_project")
    @ResponseBody
    public String updateProject(
            @RequestParam("project_id") long projectID,
            @RequestParam("project_name") String name,
            @RequestParam("description") String description,
            @RequestParam("account") String account
    ) {
        Project project = projectService.getProjectById(projectID);
        project.setName(name);
        project.setDescription(description);

        projectService.saveProject(project);

        Optional<ProjectRole> projectRole = projectRoleService.getAll().stream()
                .filter(pr -> pr.getProjectId().getId().equals(projectID))
                .findFirst();

        if (projectRole.isPresent()) {
            ProjectRole pr = projectRole.get();

            Optional<User> user = userService.getAll()
                    .stream()
                    .filter(u -> u.getAccount().equals(account)).findFirst();
            user.ifPresent(pr::setUserId);

            projectRoleService.saveProjectRole(pr);
        }

        return "ok";
    }

}
