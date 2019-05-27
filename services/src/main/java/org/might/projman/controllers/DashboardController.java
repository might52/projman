package org.might.projman.controllers;

import org.might.projman.UserPreference;
import org.might.projman.controllers.annotations.Auth;
import org.might.projman.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/main")
@Auth
public class DashboardController {

    private static final String DASHBOARD_FORM = "dashboard_form.html";
    private static final String DASHBOARD_REDIRECT = "redirect:/dashboard";

    private UserPreference userPreference;
    private TaskService taskService;

    @Autowired
    public DashboardController(
            UserPreference userPreference,
            TaskService taskService
    ) {
        this.userPreference = userPreference;
        this.taskService = taskService;
    }

    @GetMapping(value = "/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("user_pref", userPreference);
        model.addAttribute("tasks",
                taskService.getAll()
                        .stream()
                        .filter(task -> task.getAssigneId().getAccount().equals(userPreference.getUserLogin()))
                        .collect(Collectors.toList())
        );
        return DASHBOARD_FORM;
    }

}
