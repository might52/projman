package org.might.projman.services;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.might.projman.dba.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;
import java.util.List;

@SpringBootTest("StatusServiceTest")
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class StatusServiceTest {

    @Autowired
    private StatusService statusService;

    private static final String TEST_STATUS = "Tester";

    @Test
    public void addNewStatus() {
        System.out.println(String.format("Status count before cleanup: %s", statusService.getAll().size()));
        cleanupDB();
        System.out.println(String.format("Status count after cleanup: %s", statusService.getAll().size()));
        Status status = createStatus();
        System.out.println(String.format("Status count after creation new one: %s", statusService.getAll().size()));
        System.out.println(String.format("Status created: %s", status));
        statusService.getAll().forEach(el -> {
            if (el.equals(status)) {
                Assert.assertTrue(el.equals(status));
                System.out.println(String.format("Status value compared: %s", el.equals(status)));
            }
        });
    }

    @Test
    public void removeCreatedStatus() {
        System.out.println(String.format("Status count: %s", statusService.getAll().size()));
        Status status = new Status();
        status.setName(TEST_STATUS);
        statusService
                .getAll()
                .stream()
                .filter(el -> el.equals(status))
                .forEach(el -> statusService.deleteStatus(el));
        Assert.assertTrue(statusService.getAll().size() == 0);
        System.out.println(String.format("Status count after removing: %s", statusService.getAll().size()));
    }

    private void cleanupDB() {
        statusService.getAll().forEach(el -> statusService.deleteStatus(el));
    }

    private Status createStatus() {
        Status status = new Status();
        status.setName(TEST_STATUS);
        statusService.saveStatus(status);
        return statusService.getStatusById(status.getId());
    }
}

