package org.might.projman.controllers;

import org.might.projman.controllers.annotations.Auth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
@Auth
public class DashboardController {

    private static final String DASHBOARD_FORM = "dashboard_form.html";
    private static final String DASHBOARD_REDIRECT = "redirect:/dashboard";

    @GetMapping(value = "/dashboard")
    public String dashboard() {
        return DASHBOARD_FORM;
    }

}
