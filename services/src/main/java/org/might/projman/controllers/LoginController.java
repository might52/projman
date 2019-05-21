package org.might.projman.controllers;

import org.might.projman.UserPreference;
import org.might.projman.dba.model.Status;
import org.might.projman.dba.model.User;
import org.might.projman.model.LoginFormViewModel;
import org.might.projman.services.StatusService;
import org.might.projman.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    private UserPreference userPreference;
    private UserService userService;

    private static final String LOGIN_FORM_ATTR = "loginFormViewModel";

    @Autowired
    public LoginController(
            UserPreference userPreference,
            UserService userService
    ) {
        this.userPreference = userPreference;
        this.userService = userService;
        initAdmins();
    }

    @GetMapping(value = {"/", "/index", "/login"})
    public String getData(Model model) {
        model.addAttribute(LOGIN_FORM_ATTR, new LoginFormViewModel());
        return "login_form.html";
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute(LOGIN_FORM_ATTR) LoginFormViewModel loginFormViewModel) {
        String login = loginFormViewModel.getLogin();
        String password = DigestUtils.md5DigestAsHex(loginFormViewModel.getPassword().getBytes());
        for (User user : userService.getAll()) {
            if (user.getAccount().equals(login) && user.getPassword().equals(password)) {
                userPreference.setUserID(user.getId());
                userPreference.setUserLogin(login);
                break;
            }
        }

        return userPreference.getUserID() != 0 ? "redirect:/main/main_page" : "login_form.html";
    }

    private void initAdmins() {
        boolean needToSave = true;
        for (User user : userService.getAll()) {
            if ("nik709".equals(user.getAccount())) {
                needToSave = false;
            }
        }
        if (needToSave) {
            String pass = DigestUtils.md5DigestAsHex("test".getBytes());
            User nik709 = new User();
            nik709.setAccount("nik709");
            nik709.setPassword(pass);

            User might52 = new User();
            might52.setAccount("might52");
            might52.setPassword(pass);

            User VAGIK_BRAT_BRATAN_BRATISHKA = new User();
            VAGIK_BRAT_BRATAN_BRATISHKA.setAccount("Vagik");
            VAGIK_BRAT_BRATAN_BRATISHKA.setPassword(pass);

            User Vitalius = new User();
            Vitalius.setAccount("Vitaliy");
            Vitalius.setPassword(pass);

            userService.saveUser(nik709);
            userService.saveUser(might52);
            userService.saveUser(VAGIK_BRAT_BRATAN_BRATISHKA);
            userService.saveUser(Vitalius);
        }
    }

}
