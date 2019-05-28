package org.might.projman.model;

import org.might.projman.dba.model.ProjectRole;

public class ProjectRolesViewModel {
    private Long id;
    private Long projectId;
    private Long userId;
    private Long roleId;

    public ProjectRolesViewModel() {
    }

    public ProjectRolesViewModel(Long id, Long projectId, Long userId, Long roleId) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "ProjectRole{" +
                "id=" + getId() +
                ", projectId=" + getProjectId() +
                ", userId=" + getUserId() +
                ", roleId=" + getRoleId() +
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


}
