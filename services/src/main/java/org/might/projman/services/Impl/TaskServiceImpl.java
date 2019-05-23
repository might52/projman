package org.might.projman.services.Impl;

import org.might.projman.dba.model.Task;
import org.might.projman.dba.repositories.TaskRepository;
import org.might.projman.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }
}
