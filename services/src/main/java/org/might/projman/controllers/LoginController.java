package org.might.projman.controllers;

import org.might.projman.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private StatusService statusService;

    @Autowired
    public LoginController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/")
    public String getData() {
        return "index.html";
    }


}
