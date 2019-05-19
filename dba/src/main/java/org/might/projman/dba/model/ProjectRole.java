package org.might.projman.dba.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project_role")
public class ProjectRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(targetEntity = Project.class)
    private Project projectId;
    @ManyToOne(targetEntity = User.class)
    private User userId;
    @ManyToOne(targetEntity = Role.class)
    private Role roleId;

    public ProjectRole() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "ProjectRole{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectRole that = (ProjectRole) o;
        return getProjectId().equals(that.getProjectId()) &&
                getUserId().equals(that.getUserId()) &&
                getRoleId().equals(that.getRoleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectId(), getUserId(), getRoleId());
    }
}
