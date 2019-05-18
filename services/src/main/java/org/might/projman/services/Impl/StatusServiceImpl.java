package org.might.projman.services.Impl;

import org.might.projman.dba.model.Status;
import org.might.projman.dba.repositories.StatusRepository;
import org.might.projman.services.StatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    private StatusRepository statusRepository;

    @Autowired
    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Status getStatusById(Long id) {
        return this.statusRepository.getOne(id);
    }

    public List<Status> getAll() {
        return statusRepository.findAll();
    }

    public void saveStatus(Status status) {
        this.statusRepository.save(status);
    }

    public void deleteStatus(Status status) {
        this.statusRepository.delete(status);
    }
}
