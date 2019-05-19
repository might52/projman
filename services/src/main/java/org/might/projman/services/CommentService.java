package org.might.projman.services;

import org.might.projman.dba.model.Comment;

import java.util.List;

public interface CommentService {
    Comment getCommentById(Long id);
    void saveComment(Comment comment);
    void deleteComment(Comment comment);
    List<Comment> getAll();
}
