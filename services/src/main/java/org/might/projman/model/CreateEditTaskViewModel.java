package org.might.projman.model;

import org.might.projman.dba.model.Project;
import org.might.projman.dba.model.Status;
import org.might.projman.dba.model.User;

import java.util.Date;

public class CreateEditTaskViewModel extends BaseEntityViewModel {

    private Status status;
    private Project project;
    private User assigneId;
    private Date dueDate;
    private User createdBy;
    private Date creationDate;
    private Date usedTime;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getAssigneId() {
        return assigneId;
    }

    public void setAssigneId(User assigneId) {
        this.assigneId = assigneId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Date usedTime) {
        this.usedTime = usedTime;
    }
}
