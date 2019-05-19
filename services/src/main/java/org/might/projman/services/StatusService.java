package org.might.projman.services;

import org.might.projman.dba.model.Status;

import java.util.List;

public interface StatusService {
    Status getStatusById(Long id);
    void saveStatus(Status status);
    void deleteStatus(Status status);
    List<Status> getAll();
}
