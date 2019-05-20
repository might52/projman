package org.might.projman.controllers;

import org.might.projman.UserPreference;
import org.might.projman.dba.model.Status;
import org.might.projman.model.LoginFormViewModel;
import org.might.projman.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    private StatusService statusService;
    private UserPreference userPreference;


    private static final String LOGIN_FORM_ATTR = "loginFormViewModel";

    @Autowired
    public LoginController(StatusService statusService, UserPreference userPreference) {
        this.statusService = statusService;
        this.userPreference = userPreference;
        initStatusData();
    }

    @GetMapping(value = {"/", "/index", "/login"})
    public String getData(Model model) {
        model.addAttribute(LOGIN_FORM_ATTR, new LoginFormViewModel());
        return "index.html";
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute(LOGIN_FORM_ATTR) LoginFormViewModel loginFormViewModel) {
        userPreference.setUserID(123);
        userPreference.setUserLogin("IT IS AN STUB LOGIN. PLEASE FIND ME ID DB");
        return "redirect:/main/main_page";
    }

    private void initStatusData() {
        List<Status> statuses = new ArrayList<Status>() {{
            add(new Status("Assigned"));
            add(new Status("In progress"));
            add(new Status("Done"));
        }};

        List<Status> allStatutes = statusService.getAll();
        for (Status status : statuses) {
             if ( !allStatutes.contains(status) ) {
                statusService.saveStatus(status);
            }
        }
    }

}
