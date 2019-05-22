package org.might.projman.services.Impl;

import org.might.projman.dba.model.Comment;
import org.might.projman.dba.repositories.CommentRepository;
import org.might.projman.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment getCommentById(Long id) {
        // FIXME: need to rewrite via findALl().
        return commentRepository.getOne(id);
    }

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }
}
