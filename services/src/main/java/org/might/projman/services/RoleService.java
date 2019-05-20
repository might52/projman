package org.might.projman.services;

import org.might.projman.dba.model.Role;

import java.util.List;

public interface RoleService {
    Role getRoleById(Long id);
    void saveRole(Role role);
    void deleteRole(Role role);
    List<Role> getAll();
}
