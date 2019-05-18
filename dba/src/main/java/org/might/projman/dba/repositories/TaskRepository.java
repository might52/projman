package org.might.projman.dba.repositories;

import org.might.projman.dba.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
