package org.might.projman.controllers;

import org.might.projman.UserPreference;
import org.might.projman.controllers.annotations.Auth;
import org.might.projman.dba.model.Project;
import org.might.projman.dba.model.User;
import org.might.projman.model.LoginFormViewModel;
import org.might.projman.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.IntStream;

@Controller
@RequestMapping("/main")
@Auth
public class MainController {

    private static final String MAIN_FORM = "main_form.html";
    private static final String PROJECT_FORM = "project_form.html";
    private static final String TASK_FORM = "task_form.html";
    private static final String MAIN_REDIRECT = "redirect:/main/main_page";
    private static final String LOGIN_FORM_ATTR = "loginFormViewModel";
    private static final String NOT_FOUND = "not_found.html";

    private final UserPreference userPreference;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public MainController(
            UserPreference userPreference,
            ProjectService projectService,
            TaskService taskService,
            UserService userService
    ) {
        this.userPreference = userPreference;
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
        DEBUG_removeStubProjects();
        DEBUG_generateStubProjects();
    }

    @GetMapping(value = {"/"})
    public String defaultRedirect(Model model) {
        model.addAttribute(LOGIN_FORM_ATTR, new LoginFormViewModel());
        model.addAttribute("user_pref", userPreference);
        model.addAttribute("projects", projectService.getAll());
        return MAIN_REDIRECT;
    }

    @GetMapping(value = "/main_page")
    public String mainForm(Model model) {
        model.addAttribute(LOGIN_FORM_ATTR, new LoginFormViewModel());
        model.addAttribute("user_pref", userPreference);
        model.addAttribute("projects", projectService.getAll());
        return MAIN_FORM;
    }

    private void DEBUG_removeStubProjects() {
        projectService.getAll().stream().filter(project -> project.getId() != 1L).forEach(projectService::deleteProject);
    }

    private void DEBUG_generateStubProjects() {
        IntStream.range(1, 10).forEach(order -> {Project project = new Project();
            project.setName("Test project name" + order);
            project.setDescription("Test project description " + order);
            projectService.saveProject(project);
        });
    }

    @PostMapping(value = "/createUser")
    public String createUser(@ModelAttribute(LOGIN_FORM_ATTR) LoginFormViewModel loginFormViewModel) {
        String login = loginFormViewModel.getLogin();
        String password = new BCryptPasswordEncoder().encode(loginFormViewModel.getPassword());

        if (userService.getAll().stream().map(user -> user.getAccount().toUpperCase()).noneMatch(s -> s.equals(login.toUpperCase()))) {
            User user = new User();
            user.setAccount(login);
            user.setPassword(password);
            userService.saveUser(user);
        }
        return MAIN_REDIRECT;
    }

    @GetMapping(value = "/logout")
    public String logout() {
        userPreference.setUserID(0);
        userPreference.setUserLogin(null);
        return MAIN_REDIRECT;
    }

    @GetMapping(value = "/project_page")
    public String projectPage(@RequestParam("project_id") long projectId) {
        return PROJECT_FORM;
    }

    @GetMapping(value = "/not_found")
    public String notFount() {
        return NOT_FOUND;
    }
}
