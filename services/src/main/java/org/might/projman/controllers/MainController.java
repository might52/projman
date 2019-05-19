package org.might.projman.controllers;

import org.might.projman.UserPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {

    private UserPreference userPreference;

    public MainController() {}

    @Autowired
    public void setUserPreference(UserPreference userPreference) {
        this.userPreference = userPreference;
    }

    @GetMapping(value = "/main_page")
    public String mainForm(Model model) {
        model.addAttribute("user_pref", userPreference);
        return "main_form.html";
    }

}
