package org.might.projman.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = "/main_page")
    public String mainForm() {
        return "main_form.html";
    }

}
