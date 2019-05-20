package org.might.projman.services;

import org.might.projman.dba.model.Task;

import java.util.List;

public interface TaskService {
    Task getTaskById(Long id);
    void saveTask(Task task);
    void deleteTask(Task task);
    List<Task> getAll();
}
