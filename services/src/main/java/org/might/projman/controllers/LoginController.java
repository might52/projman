package org.might.projman.controllers;

import org.might.projman.UserPreference;
import org.might.projman.dba.model.ProjectRole;
import org.might.projman.dba.model.User;
import org.might.projman.model.LoginFormViewModel;
import org.might.projman.services.ProjectRoleService;
import org.might.projman.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class LoginController {

    private UserPreference userPreference;
    private UserService userService;
    private ProjectRoleService projectRoleService;

    private static final String LOGIN_FORM_ATTR = "loginFormViewModel";

    @Autowired
    public LoginController(
            UserPreference userPreference,
            UserService userService,
            ProjectRoleService projectRoleService
    ) {
        this.userPreference = userPreference;
        this.userService = userService;
        this.projectRoleService = projectRoleService;
        initAdmins();
    }

    @GetMapping(value = {"/", "/index", "/login"})
    public String getData(Model model) {
        if (userPreference.getUserID() != 0 && !userPreference.getUserLogin().isEmpty()) {
            return "redirect:/main/main_page";
        }
        model.addAttribute(LOGIN_FORM_ATTR, new LoginFormViewModel());
        return "login_form.html";
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute(LOGIN_FORM_ATTR) LoginFormViewModel loginFormViewModel) {
        String login = loginFormViewModel.getLogin();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        for (User user : userService.getAll()) {
            if (user.getAccount().equals(login) && encoder.matches(loginFormViewModel.getPassword(), user.getPassword())) {
                userPreference.setUserID(user.getId());
                userPreference.setUserLogin(login);
                Optional<ProjectRole> roleOptional = projectRoleService.getAll()
                        .stream()
                        .filter(pr -> pr.getUserId().getId().equals(user.getId())
                                && pr.getRoleId().getName().startsWith("Admin")
                        ).findFirst();
                userPreference.setAdmin(roleOptional.isPresent()
                        || user.getAccount().startsWith("nik709")
                        || user.getAccount().startsWith("Vitaliy")
                        || user.getAccount().startsWith("might52")
                );
                Optional<ProjectRole> manager = projectRoleService.getAll()
                        .stream()
                        .filter(
                                projectRole -> projectRole.getUserId().equals(user)
                                        && projectRole.getRoleId().getName().equals("Manager"))
                        .findFirst();
                userPreference.setManager(manager.isPresent());
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
            String pass = new BCryptPasswordEncoder().encode("test");
            User nik709 = new User();
            nik709.setName("Nikita");
            nik709.setSecondName("Grechukhin");
            nik709.setAccount("nik709");
            nik709.setPassword(pass);

            User might52 = new User();
            might52.setName("Andrei");
            might52.setSecondName("Fedotov");
            might52.setAccount("might52");
            might52.setPassword(pass);

            User VAGIK_BRAT_BRATAN_BRATISHKA = new User();
            VAGIK_BRAT_BRATAN_BRATISHKA.setName("Vagik");
            VAGIK_BRAT_BRATAN_BRATISHKA.setSecondName("Simonyan");
            VAGIK_BRAT_BRATAN_BRATISHKA.setAccount("Vagik");
            VAGIK_BRAT_BRATAN_BRATISHKA.setPassword(pass);

            User Vitalius = new User();
            Vitalius.setName("Vitaliy");
            Vitalius.setSecondName("Konevsky");
            Vitalius.setAccount("Vitaliy");
            Vitalius.setPassword(pass);

            userService.saveUser(nik709);
            userService.saveUser(might52);
            userService.saveUser(VAGIK_BRAT_BRATAN_BRATISHKA);
            userService.saveUser(Vitalius);
        }
    }

}
