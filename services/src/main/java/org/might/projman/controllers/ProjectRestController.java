package org.might.projman.controllers;


import org.might.projman.controllers.annotations.Auth;
import org.might.projman.controllers.dtos.ProjectDTOResponse;
import org.might.projman.dba.model.Status;
import org.might.projman.dba.model.Task;
import org.might.projman.services.ProjectRoleService;
import org.might.projman.services.ProjectService;
import org.might.projman.services.StatusService;
import org.might.projman.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ProjectRestController(TaskService taskService,
                                 ProjectRoleService projectRoleService,
                                 ProjectService projectService,
                                 StatusService statusService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.projectRoleService = projectRoleService;
        this.statusService = statusService;
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
        task.setUsedTime(new Date(task.getCreationDate().getTime() + time*60*1000));
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

}
