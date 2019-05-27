package org.might.projman.dba.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "project_role")
public class ProjectRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(targetEntity = Project.class, optional = false)
    private Project projectId;
    @OneToOne(targetEntity = User.class, optional = false)
    private User userId;
    @OneToOne(targetEntity = Role.class, optional = false)
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
    @JsonIgnore
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
                "id=" + getId() +
                ", projectId=" + getProjectId() +
                ", userId=" + getUserId().getName() +
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
