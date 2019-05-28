package org.might.projman.controllers;

import org.might.projman.UserPreference;
import org.might.projman.controllers.annotations.Auth;
import org.might.projman.model.CreateEditProjectViewModel;
import org.might.projman.model.LoginFormViewModel;
import org.might.projman.model.ProjectRolesViewModel;
import org.might.projman.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @Autowired
    public UserSettingsController(UserPreference userPreference, ProjectService projectService) {
        this.userPreference = userPreference;
        this.projectService = projectService;

    }

    @GetMapping()
    public String dashboard(Model model) {
//        model.addAttribute(LOGIN_FORM_ATTR, new LoginFormViewModel());
        model.addAttribute("user_pref", userPreference);
        model.addAttribute("projects", projectService.getAll());
//        model.addAttribute(PROJECT_FORM_ATTR, new CreateEditProjectViewModel());
//        model.addAttribute(PROJECT_ROLE_ATTR, new ProjectRolesViewModel());
        return USER_HTML;
    }

}
