package org.might.projman.controllers;

import org.might.projman.dba.model.Status;
import org.might.projman.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    private StatusService statusService;

    @Autowired
    public LoginController(StatusService statusService) {
        this.statusService = statusService;
        initStatusData();
    }


    @GetMapping("/")
    public String getData() {
        return "index.html";
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
