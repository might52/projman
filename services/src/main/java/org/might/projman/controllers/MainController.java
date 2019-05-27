package org.might.projman.controllers;

import org.might.projman.UserPreference;
import org.might.projman.controllers.annotations.Auth;
import org.might.projman.dba.model.Project;
import org.might.projman.dba.model.Task;
import org.might.projman.dba.model.User;
import org.might.projman.model.CreateEditCommentViewModel;
import org.might.projman.model.CreateEditProjectViewModel;
import org.might.projman.model.CreateEditTaskViewModel;
import org.might.projman.model.LoginFormViewModel;
import org.might.projman.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/main")
@Auth
public class MainController {

    // MAIN FORM
    private static final String MAIN_FORM = "main_form.html";
    private static final String MAIN_REDIRECT = "redirect:/main/main_page";

    // TEMPLATES
    private static final String PROJECT_FORM_ATTR = "projectFormViewModel";
    private static final String TASK_FORM = "task_form.html";
    private static final String NOT_FOUND_FORM = "not_found.html";

    // VIEW MODELS
    private static final String LOGIN_FORM_ATTR = "loginFormViewModel";

    private final UserPreference userPreference;
    private final ProjectService projectService;
    private final UserService userService;

    @Autowired
    public MainController(
            UserPreference userPreference,
            ProjectService projectService,
            UserService userService
    ) {
        this.userPreference = userPreference;
        this.projectService = projectService;
        this.userService = userService;
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
        model.addAttribute(PROJECT_FORM_ATTR, new CreateEditProjectViewModel());
        model.addAttribute("user_pref", userPreference);
        model.addAttribute("projects", projectService.getAll());
        model.addAttribute("users", userService.getAll());
        return MAIN_FORM;
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

    @PostMapping(value = "create_project")
    public String createProject(@ModelAttribute(PROJECT_FORM_ATTR) CreateEditProjectViewModel projectViewModel) {
        Project project = new Project();
        project.setName(projectViewModel.getName());
        project.setDescription(projectViewModel.getDescription());
        projectService.saveProject(project);
        return MAIN_REDIRECT;
    }

    @GetMapping(value = "/not_found")
    public String notFount() {
        return NOT_FOUND_FORM;
    }
}
