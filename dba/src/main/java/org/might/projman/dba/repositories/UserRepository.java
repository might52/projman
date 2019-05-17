package org.might.projman.dba.repositories;

import org.might.projman.dba.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
