package org.might.projman.dba.repositories;

import org.might.projman.dba.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
