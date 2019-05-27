package org.might.projman.services;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.might.projman.dba.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest("CommentServiceTest")
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class CommentServiceTest {

    @Autowired
    private StatusService statusService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    private static final String TEST_COMMENT_FIRST = "TEST_COMMENT_FIRST";
    private static final String TEST_COMMENT_SECOND = "TEST_COMMENT_SECOND";
    private static final String TEST_STATUS_ACTIVE = "TEST_STATUS_ACTIVE";
    private static final String TEST_USER_FIRST = "TEST_USER_FIRST";
    private static final String TEST_PROJECT_FIRST = "TEST_PROJECT_FIRST";
    private static final String TEST_TASK_FIRST = "TEST_TASK_FIRST";

    @Test
    public void AddComment() {
        System.out.println(String.format("Task count before cleanup: %s", taskService.getAll().size()));
        System.out.println(String.format("Status count before cleanup: %s", statusService.getAll().size()));
        System.out.println(String.format("User count before cleanup: %s", userService.getAll().size()));
        System.out.println(String.format("Project count before cleanup: %s", projectService.getAll().size()));
        System.out.println(String.format("Comments count before cleanup: %s", commentService.getAll().size()));
        cleanupDB();
        System.out.println(String.format("Task count after cleanup: %s", taskService.getAll().size()));
        System.out.println(String.format("Status count after cleanup: %s", statusService.getAll().size()));
        System.out.println(String.format("User count after cleanup: %s", userService.getAll().size()));
        System.out.println(String.format("Project count after cleanup: %s", projectService.getAll().size()));
        System.out.println(String.format("Comments count after cleanup: %s", commentService.getAll().size()));

        User user = createUser(TEST_USER_FIRST, true);
        Project project = createProject(TEST_PROJECT_FIRST, true);
        Status status = createStatus(TEST_STATUS_ACTIVE, true);
        Task task = createTask(user,user,project,status,TEST_TASK_FIRST, true);

        System.out.println(String.format("Task count after creation: %s", taskService.getAll().size()));
        System.out.println(String.format("Status count after creation: %s", statusService.getAll().size()));
        System.out.println(String.format("User count after creation: %s", userService.getAll().size()));
        System.out.println(String.format("Project count after creation: %s", projectService.getAll().size()));
        System.out.println(String.format("Comments count after creation others: %s", commentService.getAll().size()));

        Comment comment = createComment(user, task, TEST_COMMENT_FIRST, true);

        System.out.println(String.format("Comments count after creation: %s", commentService.getAll().size()));
        System.out.println(String.format("Comments created: %s", commentService.getCommentById(comment.getId())));
        Assert.assertTrue(commentService.getAll().size() == 1);
    }

    @Test
    public void changeComment() {
        System.out.println(String.format("Task count: %s", taskService.getAll().size()));
        System.out.println(String.format("Status count: %s", statusService.getAll().size()));
        System.out.println(String.format("User count: %s", userService.getAll().size()));
        System.out.println(String.format("Project count: %s", projectService.getAll().size()));
        System.out.println(String.format("Comment: %s", commentService.getAll().get(0)));

        User user = userService
                .getAll()
                .stream()
                .filter(el -> el.equals(createUser(TEST_USER_FIRST, false)))
                .collect(Collectors.toList())
                .get(0);
        Project project = projectService
                .getAll()
                .stream()
                .filter(el -> el.equals(createProject(TEST_PROJECT_FIRST, false)))
                .collect(Collectors.toList())
                .get(0);
        Status status = statusService
                .getAll()
                .stream()
                .filter(el -> el.equals(createStatus(TEST_STATUS_ACTIVE, false)))
                .collect(Collectors.toList())
                .get(0);

        Task task = taskService
                .getAll()
                .stream()
                .filter(el -> el.equals(createTask(user,user,project,status,TEST_TASK_FIRST, false)))
                .collect(Collectors.toList())
                .get(0);

        Comment comment = commentService
                .getAll()
                .stream()
                .filter(el -> el.equals(createComment(user, task, TEST_COMMENT_FIRST, false)))
                .collect(Collectors.toList())
                .get(0);
        comment.setDescription(TEST_COMMENT_SECOND);
        commentService.saveComment(comment);

        System.out.println(String.format("Comments count after creation: %s", commentService.getAll().size()));
        System.out.println(String.format("Comments changed: %s", commentService.getCommentById(comment.getId())));

        Assert.assertTrue(commentService.getAll().size() == 1 &&
                commentService.getCommentById(comment.getId()).getDescription().equals(TEST_COMMENT_SECOND));
    }

    @Test
    public void deleteComment() {
        System.out.println(String.format("Task count: %s", taskService.getAll().size()));
        System.out.println(String.format("Status count: %s", statusService.getAll().size()));
        System.out.println(String.format("User count: %s", userService.getAll().size()));
        System.out.println(String.format("Project count: %s", projectService.getAll().size()));
        System.out.println(String.format("Comment: %s", commentService.getAll().get(0)));

        User user = userService
                .getAll()
                .stream()
                .filter(el -> el.equals(createUser(TEST_USER_FIRST, false)))
                .collect(Collectors.toList())
                .get(0);
        Project project = projectService
                .getAll()
                .stream()
                .filter(el -> el.equals(createProject(TEST_PROJECT_FIRST, false)))
                .collect(Collectors.toList())
                .get(0);
        Status status = statusService
                .getAll()
                .stream()
                .filter(el -> el.equals(createStatus(TEST_STATUS_ACTIVE, false)))
                .collect(Collectors.toList())
                .get(0);

        Task task = taskService
                .getAll()
                .stream()
                .filter(el -> el.equals(createTask(user,user,project,status,TEST_TASK_FIRST, false)))
                .collect(Collectors.toList())
                .get(0);

        Comment comment = commentService
                .getAll()
                .stream()
                .filter(el -> el.equals(createComment(user, task, TEST_COMMENT_SECOND, false)))
                .collect(Collectors.toList())
                .get(0);
        commentService.deleteComment(comment);
        System.out.println(String.format("Comments count after removing: %s", commentService.getAll().size()));

        Assert.assertTrue(commentService.getAll().size() == 0);
    }

    private void cleanupDB() {
        taskService.getAll().forEach(el -> taskService.deleteTask(el));
        statusService.getAll().forEach(el -> statusService.deleteStatus(el));
        projectService.getAll().forEach(el -> projectService.deleteProject(el));
        userService.getAll().forEach(el -> userService.deleteUser(el));
        commentService.getAll().forEach(el -> commentService.deleteComment(el));
    }

    private Comment createComment(User user, Task task, String value, boolean isSave) {
        Comment comment = new Comment();
        comment.setCreatedBy(user);
        comment.setDescription(value);
        comment.setCreationDate(new Date());
        comment.setTaskId(task);
        if (isSave) {
            commentService.saveComment(comment);
        }

        return comment;
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

}

