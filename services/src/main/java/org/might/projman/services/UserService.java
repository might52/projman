package org.might.projman.services;

import org.might.projman.dba.model.User;

import java.util.List;

public interface UserService {
    User getUserVyId(Long id);
    void saveUser(User user);
    void deleteUser(User user);
    List<User> getAll();
}
