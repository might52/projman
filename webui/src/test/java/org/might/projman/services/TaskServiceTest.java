package org.might.projman.services;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.might.projman.dba.model.Project;
import org.might.projman.dba.model.Status;
import org.might.projman.dba.model.Task;
import org.might.projman.dba.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@SpringBootTest("TaskServiceTest")
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class TaskServiceTest {
    @Test
    public void test() {
        Assert.assertTrue(true);
    }

/*

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private TaskService taskService;


    private static final String TEST_STATUS_ACTIVE = "TEST_STATUS_ACTIVE";
    private static final String TEST_STATUS_PAUSED = "TEST_STATUS_PAUSED";

    private static final String TEST_USER_FIRST = "TEST_USER_FIRST";
    private static final String TEST_USER_SECOND = "TEST_USER_SECOND";

    private static final String TEST_PROJECT_FIRST = "TEST_PROJECT_FIRST";
    private static final String TEST_PROJECT_SECOND = "TEST_PROJECT_SECOND";

    private static final String TEST_TASK_FIRST = "TEST_TASK_FIRST";

    @Test
    public void createTask() {
        System.out.println(String.format("Task count before cleanup: %s", statusService.getAll().size()));
        System.out.println(String.format("Status count before cleanup: %s", statusService.getAll().size()));
        System.out.println(String.format("User count before cleanup: %s", statusService.getAll().size()));
        System.out.println(String.format("Project count before cleanup: %s", statusService.getAll().size()));
        cleanupDB();
        System.out.println(String.format("Task count after cleanup: %s", statusService.getAll().size()));
        System.out.println(String.format("Status count after cleanup: %s", statusService.getAll().size()));
        System.out.println(String.format("User count after cleanup: %s", statusService.getAll().size()));
        System.out.println(String.format("Project count after cleanup: %s", statusService.getAll().size()));

        User userFirst = createUser(TEST_USER_FIRST, true);
        User userSecond = createUser(TEST_USER_SECOND, true);
        Project project = createProject(TEST_PROJECT_FIRST, true);
        Status statusFirst = createStatus(TEST_STATUS_ACTIVE, true);
        Task task = createTask(userFirst, userSecond, project, statusFirst, TEST_TASK_FIRST, true);
        System.out.println(String.format("Created task: %s", taskService.getTaskById(task.getId())));
        Assert.assertTrue(taskService.getAll().size() == 1);
    }

    @Test
    public void removeTask() {

//        System.out.println(String.format("Status count: %s", statusService.getAll().size()));
    }

    @Test
    public void updateTask() {

    }

    @Test
    public void deleteTask() {

    }

    private void cleanupDB() {
        taskService.getAll().forEach(el -> taskService.deleteTask(el));
        statusService.getAll().forEach(el -> statusService.deleteStatus(el));
        projectService.getAll().forEach(el -> projectService.deleteProject(el));
        userService.getAll().forEach(el -> userService.deleteUser(el));
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

    private Status createStatus(String value, boolean isSave) {
        Status status = new Status();
        status.setName(value);
        if (isSave) {
            statusService.saveStatus(status);
        }

        return status;
    }

    private Task createTask(User assign, User createdBy, Project project, Status status, String value, boolean isSave) {
        Task task = new Task();
        task.setAssigneId(assign);
        task.setCreatedBy(createdBy);
        task.setCreationDate(new Date());
        task.setDueDate(new Date());
        task.setProjectId(project);
        task.setStatusId(status);
        task.setSubject(value);
        task.setDescription(value);
        task.setUsedTime(new Date());
        if (isSave) {
            taskService.saveTask(task);
        }

        return task;
    }
*/
}

