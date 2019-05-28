package org.might.projman.services.Impl;

import org.might.projman.dba.model.Comment;
import org.might.projman.dba.model.Task;
import org.might.projman.dba.repositories.TaskRepository;
import org.might.projman.services.CommentService;
import org.might.projman.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private CommentService commentService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, CommentService commentService) {
        this.taskRepository = taskRepository;
        this.commentService = commentService;
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
        List<Comment> comms = commentService
                .getAll()
                .stream()
                .filter(comm -> comm.getTaskId().equals(task))
                .collect(Collectors.toList());
        Iterator<Comment> commentIterator = comms.iterator();
        while (commentIterator.hasNext()) {
            commentService.deleteComment(commentIterator.next());
        }

        taskRepository.delete(task);
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }
}
