package org.might.projman.controllers;

import org.might.projman.UserPreference;
import org.might.projman.dba.model.Project;
import org.might.projman.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController implements IMainController {

    private static final String MAIN_FORM = "main_form.html";
    private static final String LOGIN_REDIRECT = "redirect:/login";
    private static final String MAIN_REDIRECT = "redirect:/main/main_page";

    private UserPreference userPreference;
    private ProjectService projectService;

    @Autowired
    public MainController(UserPreference userPreference, ProjectService projectService) {
        this.userPreference = userPreference;
        this.projectService = projectService;
    }

    @GetMapping(value = "/main_page")
    public String mainForm(Model model) {
        if (!validateSession()) {
            return LOGIN_REDIRECT;
        }
        model.addAttribute("user_pref", userPreference);
        model.addAttribute("projects", projectService.getAll());
        return MAIN_FORM;
    }

    @GetMapping(value = "/generatedata")
    public String generate() {
        if (!validateSession()) {
            return LOGIN_REDIRECT;
        }
        for (int i = 0; i < 10; ++i) {
            Project project = new Project();
            project.setName("Test project name" + i);
            project.setDescription("Test project description " + i);
            projectService.saveProject(project);
        }
        return MAIN_REDIRECT;
    }

    private boolean validateSession() {
        return userPreference != null && userPreference.getUserID() != 0 && !userPreference.getUserLogin().isEmpty();
    }

}
