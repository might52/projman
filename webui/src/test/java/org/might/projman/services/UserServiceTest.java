package org.might.projman.services;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.might.projman.dba.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest("UserServiceTest")
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private static final String TEST_USER = "TEST_USER";
    private static final String TEST_USER_UPDATED = "TEST_USER_UPDATED";

    @Test
    public void addNewUser() {
        System.out.println(String.format("User before cleanup count: %s", userService.getAll().size()));
        cleanupDB();
        System.out.println(String.format("User after cleanup count: %s", userService.getAll().size()));
        User testUser = createUser();
        System.out.println(String.format("User after creation count: %s", userService.getAll().size()));
        Assert.assertTrue(getEtalonUser(TEST_USER).equals(testUser));
    }

    @Test
    public void removeCreatedUser() {
        System.out.println(String.format("User before removing count: %s", userService.getAll().size()));
        User createdUser = getEtalonUser(TEST_USER);
        userService.getAll().forEach(el -> {
            if (el.equals(createdUser)) {
                userService.deleteUser(el);
            }
        });
        System.out.println(String.format("User after removing: %s", userService.getAll().size()));
        Assert.assertTrue(userService.getAll().size() == 0);
    }


    @Test
    public void updateUser() {
        User updatedUser = createUser();
        updatedUser.setName(TEST_USER_UPDATED);
        updatedUser.setSecondName(TEST_USER_UPDATED);
        updatedUser.setAccount(TEST_USER_UPDATED);
        updatedUser.setPassword(TEST_USER_UPDATED);
        userService.saveUser(updatedUser);
        User etalonForUpdateUser = getEtalonUser(TEST_USER_UPDATED);
        Assert.assertTrue(userService.getUserById(updatedUser.getId()).equals(etalonForUpdateUser));
    }

    private void cleanupDB() {
        userService.getAll().forEach(el -> userService.deleteUser(el));
    }

    private User createUser() {
        User testUser = getEtalonUser(TEST_USER);
        userService.saveUser(testUser);
        return userService.getUserById(testUser.getId());
    }

    private User getEtalonUser(String value) {
        User etalonUser = new User();
        etalonUser.setAccount(value);
        etalonUser.setPassword(value);
        etalonUser.setName(value);
        etalonUser.setSecondName(value);
        return etalonUser;
    }
}

