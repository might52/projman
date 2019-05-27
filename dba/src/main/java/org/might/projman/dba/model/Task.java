package org.might.projman.dba.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String subject;
    private String description;
    @ManyToOne(targetEntity = Status.class)
    private Status statusId;
    @ManyToOne(targetEntity = Project.class)
    private Project projectId;
    @ManyToOne(targetEntity = User.class)
    private User assigneId;
    private Date dueDate;
    @ManyToOne(targetEntity = User.class)
    private User createdBy;
    private Date creationDate;
    private Date usedTime;

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatusId() {
        return statusId;
    }

    public void setStatusId(Status statusId) {
        this.statusId = statusId;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
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

    @Override
    public String toString() {
        return "Task{" +
                "subject='" + getSubject() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", statusId=" + getStatusId() +
                ", projectId=" + getProjectId() +
                ", assigneId=" + getAssigneId() +
                ", dueDate=" + getDueDate() +
                ", createdBy=" + getCreatedBy() +
                ", creationDate=" + getCreationDate() +
                ", usedTime=" + getUsedTime() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return getSubject().equals(task.getSubject()) &&
                getProjectId().equals(task.getProjectId()) &&
                getCreatedBy().equals(task.getCreatedBy());
    }
}
