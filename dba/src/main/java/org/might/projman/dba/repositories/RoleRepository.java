package org.might.projman.dba.repositories;

import org.might.projman.dba.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
