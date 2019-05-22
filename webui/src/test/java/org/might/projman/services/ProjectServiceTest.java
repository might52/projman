package org.might.projman.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.might.projman.dba.model.Project;
import org.might.projman.dba.model.ProjectRole;
import org.might.projman.dba.model.Role;
import org.might.projman.dba.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;


@SpringBootTest("ProjectServiceTest")
@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectRoleService projectRoleService;
    @Autowired
    private RoleService roleService;

    private static final String PROJECT_NAME = "TestJUnitProject";

    @Test
    @Order(1)
    public void createProject() {
        System.out.println(String.format("Projects count: %s", projectService.getAll().size()));
        projectService.getAll().forEach(proj -> System.out.println(
                String.format("Prject name: %s, Project id: %s, Project desc: %s",
                        proj.getName(), proj.getId(), proj.getDescription())));
        Project project = new Project();
        project.setName(PROJECT_NAME);
        project.setDescription(PROJECT_NAME);
        projectService.saveProject(project);
        System.out.println(String.format("Projects count: %s", projectService.getAll().size()));
        projectService.getAll().forEach(proj -> System.out.println(
                String.format("Prject name: %s, Project id: %s, Project desc: %s",
                        proj.getName(), proj.getId(), proj.getDescription())));
        Assert.assertTrue(projectService.getAll().contains(project));
    }

    @Test
    @Order(2)
    public void deleteProjectWithRelations() {
        System.out.println(String.format("Projects count: %s", projectService.getAll().size()));
        projectService.getAll().forEach(proj -> System.out.println(
                String.format("Prject name: %s, projectService.deleteProject(projectService.getAll().stream().findFirst().get());Project id: %s, Project desc: %s",
                        proj.getName(), proj.getId(), proj.getDescription())));

        projectService.deleteProject(projectService.getAll().stream().findFirst().get());

        Project project = new Project();
        project.setName(PROJECT_NAME);
        project.setDescription(PROJECT_NAME);
        projectService.saveProject(project);
        System.out.println(String.format("Projects count: %s", projectService.getAll().size()));
        projectService.getAll().forEach(proj -> System.out.println(
                String.format("Project name: %s, Project id: %s, Project desc: %s",
                        proj.getName(), proj.getId(), proj.getDescription())));

        User user = new User();
        user.setName(PROJECT_NAME);
        user.setAccount(PROJECT_NAME);
        user.setPassword(PROJECT_NAME);
        user.setSecondName(PROJECT_NAME);
        userService.saveUser(user);


        Role role = new Role();
        role.setName(PROJECT_NAME);
        roleService.saveRole(role);

        Project finalProject = projectService.getAll().stream().filter(proj -> proj.equals(project)).findFirst().get();

        ProjectRole projectRole = new ProjectRole();
        projectRole.setProjectId(project);
        projectRole.setUserId(user);
        projectRole.setRoleId(role);
//        projectRole.setProjectId(finalProject);
//        projectRole.setRoleId(roleService.getAll().stream().filter(
//                u -> u.getName().equals(PROJECT_NAME)).findFirst().get());
//        projectRole.setUserId(userService.getAll().stream().filter(
//                u -> u.getName().equals(PROJECT_NAME)).findFirst().get());
        projectRoleService.saveProjectRole(projectRole);

        User userInDB = userService.getAll().stream().findFirst().get();
        System.out.println("!!!!!!!!!!!!!!!!!!  " + userInDB);

        projectService.deleteProject(finalProject);

        System.out.println(String.format("Projects count: %s", projectService.getAll().size()));
        projectService.getAll().forEach(proj -> System.out.println(
                String.format("Project name: %s, Project id: %s, Project desc: %s",
                        proj.getName(), proj.getId(), proj.getDescription())));

        Assert.assertTrue(projectService.getAll().contains(project));

    }
}
