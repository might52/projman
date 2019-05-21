package org.might.projman.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    private static final String TEST_ROLE = "Tester";

    @Test
    @Order(1)
    public void addNewRole() {
        Role testRole = new Role();
        testRole.setName(TEST_ROLE);
        System.out.println(String.format("Roles count: %s", roleService.getAll().size()));
        for (Role role : roleService.getAll()) {
            System.out.println(String.format("Role: %s, %s", role.getId(), role.getName()));
            if (role.getName().equals(testRole.getName())) {
                System.out.println(String.format("Role is found: %s, %s", role.getId(), role.getName()));
                Assert.assertTrue(role.equals(testRole));
                return;
            }
        }

        if (roleService.getAll().stream().filter(role -> role.getName().equals(testRole.getName())).count() == 0) {
            roleService.saveRole(testRole);
        }

        for (Role role : roleService.getAll()) {
            if (role.getName().equals(testRole.getName())) {
                Assert.assertTrue(role.equals(testRole));
            }
        }
    }

    @Test
    @Order(2)
    public void removeCreatedRoles() {
        System.out.println(String.format("Roles count: %s", roleService.getAll().size()));
        if (roleService.getAll().size() == 0) {
            Assert.assertTrue(false);
        }

/*
        List<Role> roles = roleService
                .getAll()
                .stream()
                .filter(role -> role.getName().equals(TEST_ROLE))
                .collect(Collectors.toList());
*/
        List<Role> roles = roleService.getAll();
        Iterator<Role> iterator = roles.iterator();
        while (iterator.hasNext()) {
            roleService.deleteRole(iterator.next());
        }

        System.out.println(String.format("Roles count after removing: %s", roleService.getAll().size()));
        Assert.assertTrue(roleService.getAll().size() == 0);
    }
}

