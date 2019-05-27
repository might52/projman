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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@SpringBootTest("ProjectRoleServiceTest")
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class ProjectRoleServiceTest {

    @Autowired
    private ProjectRoleService projectRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ProjectService projectService;

    private static final String TEST_USER = "TEST_USER";
    private static final String TEST_USER_UPDATED = "TEST_USER_UPDATED";
    private static final String TEST_ROLE = "TEST_ROLE";
    private static final String TEST_ROLE_UPDATED = "TEST_ROLE_UPDATED";
    private static final String TEST_PROJECT = "TEST_PROJECT";
    private static final String TEST_PROJECT_UPDATED = "TEST_PROJECT_UPDATED";

    @Test
    public void addNewProjectRole() {
        System.out.println(String.format("count projectRoles before cleanup: %s", projectRoleService.getAll().size()));
        System.out.println(String.format("count users before cleanup: %s", userService.getAll().size()));
        System.out.println(String.format("count roles before cleanup: %s", roleService.getAll().size()));
        System.out.println(String.format("count projects before cleanup: %s", projectService.getAll().size()));

        cleanupDB();

        System.out.println(String.format("count projectRoles after cleanup: %s", projectRoleService.getAll().size()));
        System.out.println(String.format("count users after cleanup: %s", userService.getAll().size()));
        System.out.println(String.format("count roles after cleanup: %s", roleService.getAll().size()));
        System.out.println(String.format("count projects after cleanup: %s", projectService.getAll().size()));

        User user = createUser(TEST_USER, true);
        Project project = createProject(TEST_PROJECT,true);
        Role role = createRole(TEST_ROLE,true);
        ProjectRole projectRole = createProjectRole(user, role, project, true);

        System.out.println(String.format("count projectRoles after creation: %s", projectRoleService.getAll().size()));
        System.out.println(String.format("count users after creation: %s", userService.getAll().size()));
        System.out.println(String.format("count roles after creation: %s", roleService.getAll().size()));
        System.out.println(String.format("count projects after creation: %s", projectService.getAll().size()));

        Assert.assertTrue(projectRoleService.getAll().size() == 1);
        Assert.assertTrue(projectService.getAll().size() == 1);
        Assert.assertTrue(userService.getAll().size() == 1);
        Assert.assertTrue(roleService.getAll().size() == 1);

        System.out.println(String.format("Created projectRole: %s", projectRole));
    }

    @Test
    public void removeProjectRole() {
        System.out.println(String.format("count projectRoles before removing: %s", projectRoleService.getAll().size()));
        System.out.println(String.format("count users before removing: %s", userService.getAll().size()));
        System.out.println(String.format("count roles before removing: %s", roleService.getAll().size()));
        System.out.println(String.format("count projects before removing: %s", projectService.getAll().size()));
        ProjectRole projectRole = createProjectRole(
                createUser(TEST_USER,false),
                createRole(TEST_ROLE,false),
                createProject(TEST_PROJECT,false),
                false);
        projectRoleService.getAll().forEach(el -> {
            if (el.equals(projectRole)) {
                projectRoleService.deleteProjectRole(el);
            }
        });

        System.out.println(String.format("count projectRoles after removing: %s", projectRoleService.getAll().size()));
        System.out.println(String.format("count users after removing: %s", userService.getAll().size()));
        System.out.println(String.format("count roles after removing: %s", roleService.getAll().size()));
        System.out.println(String.format("count projects after removing: %s", projectService.getAll().size()));

        Assert.assertTrue(projectRoleService.getAll().size() == 0);
    }

    @Test
    public void updateProjectRole() {
        cleanupDB();
        User user = createUser(TEST_USER,true);
        Role role = createRole(TEST_ROLE,true);
        Project project = createProject(TEST_PROJECT,true);
        ProjectRole projectRole = createProjectRole(user, role, project, true);
        User updatedUser = createUser(TEST_USER_UPDATED,true);
        Role updatedRole = createRole(TEST_ROLE_UPDATED, true);
        Project updatedProject = createProject(TEST_PROJECT_UPDATED, true);

        System.out.println(String.format("Project role before update: %s", projectRole));

        projectRole.setProjectId(updatedProject);
        projectRole.setUserId(updatedUser);
        projectRole.setRoleId(updatedRole);

        projectRoleService.saveProjectRole(projectRole);

        System.out.println(String.format("Project role updated: %s", projectRole));

        ProjectRole updatedProjectRole = new ProjectRole();
        updatedProjectRole.setRoleId(updatedRole);
        updatedProjectRole.setUserId(updatedUser);
        updatedProjectRole.setProjectId(updatedProject);

        Assert.assertTrue(projectRole.equals(updatedProjectRole));
    }

    private void cleanupDB () {
        projectRoleService.getAll().forEach(el -> projectRoleService.deleteProjectRole(el));
        roleService.getAll().forEach(el -> roleService.deleteRole(el));
        userService.getAll().forEach(el -> userService.deleteUser(el));
        projectService.getAll().forEach(el -> projectService.deleteProject(el));
    }

    private User createUser(String value, boolean isSave) {
        User user = new User();
        user.setName(value);
        user.setSecondName(value);
        user.setAccount(value);
        user.setPassword(value);
        if (isSave) {
            userService.saveUser(user);
        }

        return user;
    }

    private Project createProject(String value, boolean isSave) {
        Project project = new Project();
        project.setName(value);
        project.setDescription(value);
        if (isSave) {
            projectService.saveProject(project);
        }

        return project;
    }

    private Role createRole(String value, boolean isSave) {
        Role role = new Role();
        role.setName(value);
        if (isSave) {
            roleService.saveRole(role);
        }

        return role;
    }

    private ProjectRole createProjectRole(User user, Role role, Project project, boolean isSave) {
        ProjectRole projectRole = new ProjectRole();
        projectRole.setRoleId(role);
        projectRole.setUserId(user);
        projectRole.setProjectId(project);
        if (isSave) {
            projectRoleService.saveProjectRole(projectRole);
        }

        return projectRole;
    }

}

