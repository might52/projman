package org.might.projman.services;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.might.projman.dba.model.Project;
import org.might.projman.dba.model.ProjectRole;
import org.might.projman.dba.model.Role;
import org.might.projman.dba.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;
import java.util.List;

@SpringBootTest("ProjectServiceTest")
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
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
    private static final String PROJECT_NAME_SECOND = "TestJUnitProject_second";

    @Test
    public void createProject() {
        cleanupDatabase();
        System.out.println(String.format("Projects count: %s", projectService.getAll().size()));
        projectService.getAll().forEach(proj -> System.out.println(
                String.format("Prject name: %s, Project id: %s, Project desc: %s",
                        proj.getName(), proj.getId(), proj.getDescription())));
        Project project = createTestProject();
        System.out.println(String.format("Projects count: %s", projectService.getAll().size()));
        projectService.getAll().forEach(proj -> System.out.println(
                String.format("Prject name: %s, Project id: %s, Project desc: %s",
                        proj.getName(), proj.getId(), proj.getDescription())));
        System.out.println(String.format("Created projects: %s", project));
        Assert.assertTrue(projectService.getAll().contains(project));
    }

    @Test
    public void deleteProjectWithRelations() {
        System.out.println(String.format("Projects count: %s", projectService.getAll().size()));
        projectService.getAll().forEach(proj -> System.out.println(
                String.format("Prject name: %s, projectService.deleteProject(projectService.getAll().stream().findFirst().get());Project id: %s, Project desc: %s",
                        proj.getName(), proj.getId(), proj.getDescription())));

        cleanupDatabase();

        Project project = createTestProject();
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
        projectRoleService.saveProjectRole(projectRole);

        System.out.println(userService.getAll().get(0));
        System.out.println(roleService.getAll().get(0));
        System.out.println(projectService.getAll().get(0));
        System.out.println(projectRoleService.getAll().get(0));

        projectService.deleteProject(finalProject);

        System.out.println(String.format("Projects count: %s", projectService.getAll().size()));
        projectService.getAll().forEach(proj -> System.out.println(
                String.format("Project name: %s, Project id: %s, Project desc: %s",
                        proj.getName(), proj.getId(), proj.getDescription())));

        Assert.assertFalse(projectService.getAll().contains(finalProject));
    }

    @Test
    public void updateProject() {
        System.out.println(String.format("Projects count: %s", projectService.getAll().size()));


        System.out.println(String.format("Projects count after creation: %s", projectService.getAll().size()));

        projectService.getAll().forEach(proj -> System.out.println(
                String.format("Prject name: %s, projectService.deleteProject(projectService.getAll().stream().findFirst().get());Project id: %s, Project desc: %s",
                        proj.getName(), proj.getId(), proj.getDescription())));

        Project project = createTestProject();

        project = projectService.getProjectById(projectService
                .getAll()
                .stream()
                .filter(proj -> proj.getName().equals(PROJECT_NAME))
                .findFirst().get().getId());

        project.setName(PROJECT_NAME_SECOND);
        project.setDescription(PROJECT_NAME_SECOND);

        projectService.saveProject(project);

        Project project_second = projectService.getProjectById(project.getId());

        System.out.println(String.format("Updated project: %s", project));

        Assert.assertTrue(project_second.getName().equals(PROJECT_NAME_SECOND));

    }

    private Project createTestProject(){
        Project project = new Project();
        project.setName(PROJECT_NAME);
        project.setDescription(PROJECT_NAME);
        projectService.saveProject(project);
        return project;
    }

    private void cleanupDatabase(){
        if (projectService.getAll().size() != 0) {
            List<Project> projects = projectService.getAll();
            Iterator<Project> iterator = projects.iterator();
            while (iterator.hasNext()) {
                projectService.deleteProject(projectService.getProjectById(iterator.next().getId()));
            }
        }

        if (userService.getAll().size() != 0) {
            List<User> users = userService.getAll();
            Iterator<User> iterator = users.iterator();
            while (iterator.hasNext()) {
                userService.deleteUser(userService.getUserById(iterator.next().getId()));
            }
        }

        if (roleService.getAll().size() != 0) {
            List<Role> roles = roleService.getAll();
            Iterator<Role> iterator = roles.iterator();
            while (iterator.hasNext()) {
                roleService.deleteRole(roleService.getRoleById(iterator.next().getId()));
            }
        }
    }

}


