package org.might.projman.controllers;

import org.might.projman.UserPreference;
import org.might.projman.controllers.annotations.Auth;
import org.might.projman.dba.model.*;
import org.might.projman.model.*;
import org.might.projman.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/main")
@Auth
public class ProjectController {

    private static final String PROJECT_FORM = "project_form.html";
    private static final String MACROS_PROJECT_ID = "@{project_id}";
    private static final String PROJECT_REDIRECT = "redirect:/main/project_page?project_id=" + MACROS_PROJECT_ID;


    private static final String PROJECT_FORM_ATTR = "projectFormViewModel";
    private static final String TASK_FORM_ATTR = "taskFormViewModel";
    private static final String COMMENT_FORM_ATTR = "commentFormViewModel";

    private final UserPreference userPreference;
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final ProjectRoleService projectRoleService;

    @Autowired
    public ProjectController(ProjectService projectService,
                             UserPreference userPreference,
                             UserService userService,
                             TaskService taskService,
                             ProjectRoleService projectRoleService) {
        this.userPreference = userPreference;
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
        this.projectRoleService = projectRoleService;

    }

    @GetMapping(value = "/project_page")
    public String projectPage(@RequestParam("project_id") long projectId, Model model) {
        model.addAttribute("user_pref", userPreference);
        Project project = projectService.getProjectById(projectId);
        model.addAttribute(PROJECT_FORM_ATTR, new CreateEditProjectViewModel());
        model.addAttribute(TASK_FORM_ATTR, new CreateEditTaskViewModel());
        model.addAttribute("project", project);
        model.addAttribute("tasks", taskService.getAll().stream().filter(task -> task.getProjectId() != null && task.getProjectId().getId() == projectId).collect(Collectors.toList()));
        return PROJECT_FORM;
    }

    @GetMapping(value = "/task_page")
    public String taskPage(@RequestParam("task_id") long taskID, Model model) {
        model.addAttribute("user_pref", userPreference);
        Task task = taskService.getTaskById(taskID);
        model.addAttribute(TASK_FORM_ATTR, new CreateEditTaskViewModel());
        model.addAttribute(COMMENT_FORM_ATTR, new CreateEditCommentViewModel());
        model.addAttribute("task", task);
        return PROJECT_REDIRECT;
    }

    @PostMapping(value = "create_task")
    public String createTask(@RequestParam("project_id") long projectId, @ModelAttribute(TASK_FORM_ATTR) CreateEditTaskViewModel taskViewModel) {
        Task task = new Task();
        task.setSubject(taskViewModel.getName());
        task.setDescription(taskViewModel.getDescription());
        task.setProjectId(projectService.getProjectById(projectId));
        task.setCreatedBy(userService.getUserById(userPreference.getUserID()));
        task.setCreationDate(new Date());
        taskService.saveTask(task);
        return PROJECT_REDIRECT.replace(MACROS_PROJECT_ID, projectId + "");
    }

}
