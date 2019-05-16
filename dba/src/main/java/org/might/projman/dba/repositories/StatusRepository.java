package org.might.projman.dba.repositories;

import org.might.projman.dba.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {

}
