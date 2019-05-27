package org.might.projman.controllers;

import org.might.projman.UserPreference;
import org.might.projman.controllers.annotations.Auth;
import org.might.projman.dba.model.Project;
import org.might.projman.dba.model.Role;
import org.might.projman.dba.model.Status;
import org.might.projman.dba.model.Task;
import org.might.projman.model.CreateEditCommentViewModel;
import org.might.projman.model.CreateEditProjectViewModel;
import org.might.projman.model.CreateEditTaskViewModel;
import org.might.projman.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/main")
@Auth
public class ProjectController {

    private static final String PROJECT_FORM = "project_form.html";
    private static final String MACROS_PROJECT_ID = "@{project_id}";
    private static final String PROJECT_REDIRECT = "redirect:/main/project_page?project_id=" + MACROS_PROJECT_ID;

    private static final String TASK_FORM = "task_form.html";

    private static final String PROJECT_FORM_ATTR = "projectFormViewModel";
    private static final String TASK_FORM_ATTR = "taskFormViewModel";
    private static final String COMMENT_FORM_ATTR = "commentFormViewModel";

    private final UserPreference userPreference;
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final ProjectRoleService projectRoleService;
    private final StatusService statusService;
    private final RoleService roleService;
    private final CommentService commentService;

    @Autowired
    public ProjectController(ProjectService projectService,
                             UserPreference userPreference,
                             UserService userService,
                             TaskService taskService,
                             ProjectRoleService projectRoleService,
                             StatusService statusService,
                             RoleService roleService,
                             CommentService commentService) {
        this.userPreference = userPreference;
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
        this.projectRoleService = projectRoleService;
        this.statusService = statusService;
        this.roleService = roleService;
        this.commentService = commentService;

        if (statusService.getAll().isEmpty()) {
            new ArrayList<String>(3) {{
                add("Assigned");
                add("Completed");
                add("In Progress");
            }}.stream().map(this::createStatus).forEach(statusService::saveStatus);
        }

        if (roleService.getAll().isEmpty()) {
            new ArrayList<String>(3) {{
                add("Admin");
                add("Manager");
                add("Employee");
            }}.stream().map(this::createRole).forEach(roleService::saveRole);
        }

    }

    @GetMapping(value = "/project_page")
    public String projectPage(@RequestParam("project_id") long projectId, Model model) {
        model.addAttribute("user_pref", userPreference);
        Project project = projectService.getProjectById(projectId);
        model.addAttribute(PROJECT_FORM_ATTR, new CreateEditProjectViewModel());
        model.addAttribute(TASK_FORM_ATTR, new CreateEditTaskViewModel());
        model.addAttribute("project", project);
        model.addAttribute("projects_number", projectService.getAll().size());
        model.addAttribute("users", userService.getAll());
        model.addAttribute("tasks",
                taskService.getAll()
                        .stream()
                        .filter(task -> task.getProjectId() != null && task.getProjectId().getId() == projectId)
                        .collect(Collectors.toList())
        );
        return PROJECT_FORM;
    }

    @GetMapping(value = "/task_page")
    public String taskPage(@RequestParam("task_id") long taskID, Model model) {
        model.addAttribute("user_pref", userPreference);
        Task task = taskService.getTaskById(taskID);
        model.addAttribute(TASK_FORM_ATTR, new CreateEditTaskViewModel());
        model.addAttribute(COMMENT_FORM_ATTR, new CreateEditCommentViewModel());
        model.addAttribute("task", task);
        model.addAttribute("projects_number", projectService.getAll().size());
        model.addAttribute("comments",
                commentService.getAll().stream().filter(c -> c.getTaskId().getId().equals(taskID)).collect(Collectors.toList())
        );
        return TASK_FORM;
    }

    @PostMapping(value = "create_task")
    public String createTask(@RequestParam("project_id") long projectId, @ModelAttribute(TASK_FORM_ATTR) CreateEditTaskViewModel taskViewModel) throws ParseException {
        if (taskService.getAll().stream().noneMatch(task -> task.getSubject().equals(taskViewModel.getName()))) {
            Task task = new Task();
            task.setSubject(taskViewModel.getName());
            task.setDescription(taskViewModel.getDescription());
            task.setProjectId(projectService.getProjectById(projectId));
            task.setCreatedBy(userService.getUserById(userPreference.getUserID()));

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            task.setDueDate(simpleDateFormat.parse(taskViewModel.getDeadline()));

            task.setCreationDate(new Date());
            task.setStatusId(statusService.getAll().stream().filter(status -> status.getName().startsWith("Assigned")).collect(Collectors.toList()).get(0));
            taskService.saveTask(task);
        }
        return PROJECT_REDIRECT.replace(MACROS_PROJECT_ID, projectId + "");
    }

    private Status createStatus(String name) {
        Status status = new Status();
        status.setName(name);
        return status;
    }

    private Role createRole(String name) {
        Role role = new Role();
        role.setName(name);
        return role;
    }

}
