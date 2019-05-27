package org.might.projman.services;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.might.projman.dba.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

//@RunWith(SpringRunner.class)
//@ContextConfiguration(locations = "")
@SpringBootTest("RoleServiceTest")
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    private static final String TEST_ROLE = "Tester";

    @Test
    public void addNewRole() {
        System.out.println(String.format("Roles count: %s", roleService.getAll().size()));
        cleanupDB();
        Role testRole = createRole();
        System.out.println(String.format("Roles count after creation: %s", roleService.getAll().size()));
        System.out.println(String.format("Created role: %s", testRole));
        Role role = new Role();
        role.setName(TEST_ROLE);
        Assert.assertTrue(role.equals(testRole));
    }

    @Test
    public void removeCreatedRoles() {
        cleanupDB();
        Role role = createRole();
        System.out.println(String.format("Roles count: %s", roleService.getAll().size()));
        roleService.deleteRole(roleService.getRoleById(role.getId()));
        System.out.println(String.format("Roles count after removing: %s", roleService.getAll().size()));
        Assert.assertTrue(roleService.getAll().size() == 0);
    }

    private void cleanupDB() {
        roleService.getAll().forEach(el -> roleService.deleteRole(el));
    }

    private Role createRole() {
        Role testRole = new Role();
        testRole.setName(TEST_ROLE);
        roleService.saveRole(testRole);
        return roleService.getRoleById(testRole.getId());
    }
}

