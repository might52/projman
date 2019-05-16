package org.might.projman.dba.repositories;

import org.might.projman.dba.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
