package org.might.projman.controllers;

import org.might.projman.UserPreference;
import org.might.projman.controllers.annotations.Auth;
import org.might.projman.dba.model.*;
import org.might.projman.model.*;
import org.might.projman.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final TaskService taskService;
    private final RoleService roleService;
    private final ProjectRoleService projectRoleService;


    @Autowired
    public MainController(
            UserPreference userPreference,
            ProjectService projectService,
            UserService userService,
            TaskService taskService,
            RoleService roleService,
            ProjectRoleService projectRoleService) {
        this.userPreference = userPreference;
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
        this.roleService = roleService;
        this.projectRoleService = projectRoleService;
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
        List<Task> allTasks = taskService.getAll();
        model.addAttribute("projects",
                projectService.getAll()
                        .stream()
                        .map(
                                project -> new ProjectStat(
                                        project.getId(),
                                        project.getName(),
                                        project.getDescription(),
                                        allTasks.stream().filter(t -> t.getProjectId().getId().equals(project.getId())).count(),
                                        allTasks.stream().filter(task -> task.getProjectId().getId().equals(project.getId()) && task.getStatusId().getName().startsWith("Assigned")).count(),
                                        allTasks.stream().filter(task -> task.getProjectId().getId().equals(project.getId()) && task.getStatusId().getName().startsWith("In Progress")).count(),
                                        allTasks.stream().filter(task -> task.getProjectId().getId().equals(project.getId()) && task.getStatusId().getName().startsWith("Completed")).count(),
                                        getManagerName(project),
                                        projectRoleService.getAll().stream().filter(pr -> pr.getProjectId().getId().equals(project.getId())).count()
                                )
                        ).collect(Collectors.toList())
        );
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
            user.setName(loginFormViewModel.getFirstName());
            user.setSecondName(loginFormViewModel.getSecondName());
            userService.saveUser(user);

            Optional<Role> role = roleService.getAll().stream().filter(r -> r.getName().equals("Employee")).findFirst();

            projectService.getAll().forEach(project -> {
                ProjectRole projectRole = new ProjectRole();
                role.ifPresent(projectRole::setRoleId);
                projectRole.setUserId(user);
                projectRole.setProjectId(project);
                projectRoleService.saveProjectRole(projectRole);
            });


        }
        return MAIN_REDIRECT;
    }

    @GetMapping(value = "/logout")
    public String logout() {
        userPreference.setUserID(0);
        userPreference.setUserLogin(null);
        return MAIN_REDIRECT;
    }

    @PostMapping(value = "/create_project")
    public String createProject(@ModelAttribute(PROJECT_FORM_ATTR) CreateEditProjectViewModel projectViewModel) {

        Optional<Project> optionalProject = projectService.getAll()
                .stream()
                .filter(p -> p.getName().equals(projectViewModel.getName()))
                .findFirst();
        if (optionalProject.isPresent()) {
            return MAIN_REDIRECT;
        }

        Project project = new Project();

        project.setName(projectViewModel.getName());
        project.setDescription(projectViewModel.getDescription());

        Optional<Role> role = roleService.getAll()
                .stream()
                .filter(r -> r.getName().startsWith("Manager"))
                .findFirst();
        ProjectRole projectRole = null;
        if (role.isPresent()) {
            projectRole = new ProjectRole();
            projectRole.setProjectId(project);
            projectRole.setRoleId(role.get());
            Optional<User> userAccount = userService.getAll()
                    .stream()
                    .filter(u -> u.getAccount().equals(projectViewModel.getAccount()))
                    .findFirst();
            if (userAccount.isPresent()) {
                projectRole.setUserId(userAccount.get());
            }
        }

        projectService.saveProject(project);
        if (projectRole != null) {
            projectRoleService.saveProjectRole(projectRole);

            ProjectRole finalProjectRole = projectRole;
            userService.getAll().forEach(user -> {
                if (!user.getId().equals(finalProjectRole.getUserId().getId())) {
                    ProjectRole anotherProjectRole = new ProjectRole();
                    anotherProjectRole.setProjectId(project);
                    anotherProjectRole.setUserId(user);
                    anotherProjectRole.setRoleId(roleService.getAll()
                            .stream()
                            .filter(r -> r.getName().startsWith("Employee"))
                            .findFirst().get());
                    projectRoleService.saveProjectRole(anotherProjectRole);
                }
            });


        }
        return MAIN_REDIRECT;
    }

    @GetMapping(value = "/not_found")
    public String notFount() {
        return NOT_FOUND_FORM;
    }

    private String getManagerName(Project project) {
        List<ProjectRole> l = projectRoleService.getAll()
                .stream()
                .filter(r -> r.getProjectId().getId().equals(project.getId())
                        && r.getRoleId().getName().startsWith("Manager"))
                .collect(Collectors.toList());
        return l.isEmpty() ? "" : l.get(0).getUserId().getName() + " " + l.get(0).getUserId().getSecondName();
    }
}
