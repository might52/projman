package org.might.projman.controllers;

import org.might.projman.UserPreference;
import org.might.projman.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tools")
public class DBToolsController {

    private StatusService statusService;
    private CommentService commentService;
    private ProjectService projectService;
    private ProjectRoleService projectRoleService;
    private RoleService roleService;
    private TaskService taskService;
    private UserService userService;

    private UserPreference userPreference;

    private static final String TABLE_NAME_ATTRIBUTE = "tableName";

    @Autowired
    public DBToolsController(StatusService statusService, CommentService commentService,
                             ProjectService projectService, ProjectRoleService projectRoleService,
                             RoleService roleService, TaskService taskService,
                             UserService userService, UserPreference userPreference) {
        this.statusService = statusService;
        this.commentService = commentService;
        this.projectService = projectService;
        this.projectRoleService = projectRoleService;
        this.roleService = roleService;
        this.taskService = taskService;
        this.userService = userService;
        this.userPreference = userPreference;
    }

    @GetMapping(value = {"/dbtool"})
    public String getAll(Model model) {
        List<Object> objects = new ArrayList<>();
        objects.addAll(statusService.getAll());
        objects.addAll(commentService.getAll());
        objects.addAll(projectRoleService.getAll());
        objects.addAll(projectService.getAll());
        objects.addAll(roleService.getAll());
        objects.addAll(taskService.getAll());
        objects.addAll(userService.getAll());
        model.addAttribute("elems", objects);
        return "dbtool.html";
    }

    @GetMapping(value = {"/dbtool/status"})
    public String getStatuses(Model model) {
        model.addAttribute("elems", statusService.getAll());
        return "dbtool.html";
    }

    @GetMapping(value = {"/dbtool/role"})
    public String getRoles(Model model) {
        model.addAttribute("elems", roleService.getAll());
        return "dbtool.html";
    }

    @GetMapping(value = {"/dbtool/comment"})
    public String getComments(Model model) {
        model.addAttribute("elems", commentService.getAll());
        return "dbtool.html";
    }

    @GetMapping(value = {"/dbtool/project"})
    public String getProjects(Model model) {
        model.addAttribute("elems", projectService.getAll());
        return "dbtool.html";
    }

    @GetMapping(value = {"/dbtool/project_role"})
    public String getProjectRoles(Model model) {

        model.addAttribute("elems", projectRoleService.getAll());
        return "dbtool.html";
    }

    @GetMapping(value = {"/dbtool/task"})
    public String getTasks(Model model) {
        model.addAttribute("elems", taskService.getAll());
        return "dbtool.html";
    }

    @GetMapping(value = {"/dbtool/user"})
    public String getUsers(Model model) {
        model.addAttribute("elems", userService.getAll());
        return "dbtool.html";
    }
}