package org.might.projman.controllers;

import org.might.projman.UserPreference;
import org.might.projman.controllers.annotations.Auth;
import org.might.projman.dba.model.Project;
import org.might.projman.dba.model.ProjectRole;
import org.might.projman.dba.model.Task;
import org.might.projman.model.CreateEditProjectViewModel;
import org.might.projman.model.LoginFormViewModel;
import org.might.projman.model.ProjectRolesViewModel;
import org.might.projman.model.ProjectStat;
import org.might.projman.services.ProjectRoleService;
import org.might.projman.services.ProjectService;
import org.might.projman.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@Auth
public class UserSettingsController {

    private static final String USER_HTML = "user.html";
    private static final String DASHBOARD_REDIRECT = "redirect:/user";
    private static final String LOGIN_FORM_ATTR = "loginFormViewModel";
    private static final String PROJECT_FORM_ATTR = "projectFormViewModel";
    private static final String PROJECT_ROLE_ATTR = "projectRoleViewModel";

    private UserPreference userPreference;
    private ProjectService projectService;
    private final ProjectRoleService projectRoleService;


    @Autowired
    public UserSettingsController(UserPreference userPreference,
                                  ProjectService projectService,
                                  ProjectRoleService projectRoleService) {
        this.userPreference = userPreference;
        this.projectService = projectService;
        this.projectRoleService = projectRoleService;
    }

    @GetMapping()
    public String dashboard(Model model) {
        model.addAttribute("user_pref", userPreference);
        model.addAttribute("projects",
                projectService.getAll()
                        .stream()
                        .map(project -> new ProjectStat(
                                        project.getId(),
                                        project.getName(),
                                        project.getDescription(),
                                        1,
                                        1,
                                        1,
                                        1,
                                        "",
                                        projectRoleService.getAll().stream().filter(pr -> pr.getProjectId().getId().equals(project.getId())).count()
                                )
                        ).collect(Collectors.toList())
        );
        model.addAttribute("projectRoles", projectRoleService.getAll());

        return USER_HTML;
    }

}
