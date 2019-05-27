package org.might.projman.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.might.projman.dba.model.Project;
import org.might.projman.dba.model.ProjectRole;
import org.might.projman.dba.model.Role;
import org.might.projman.dba.model.Task;
import org.might.projman.model.ProjectStat;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProjectDTOResponse implements Serializable {

    private Long id;
    private String name;
    private String description;

    private int total;
    private long assignedCount;
    private long inProgressCount;
    private long completedCount;

    private int assignedPercent;
    private long inProgressPercent;
    private long completedPercent;

    private String managerName;

    private long employeeCount;

    @JsonIgnore
    public static ProjectDTOResponse createFromEntity(Project project, List<Task> tasks, List<ProjectRole> roles) {
        ProjectDTOResponse projectDTO = new ProjectDTOResponse();
        projectDTO.id = project.getId();
        projectDTO.name = project.getName();
        projectDTO.description = project.getDescription();

        List<Task> projectTasks = tasks.stream()
                .filter(t -> t.getProjectId().getId().equals(project.getId()))
                .collect(Collectors.toList());
        projectDTO.total = projectTasks.size();
        projectDTO.assignedCount = projectTasks.stream().filter(t -> t.getStatusId().getName().startsWith("Assigned")).count();
        projectDTO.inProgressCount = projectTasks.stream().filter(t -> t.getStatusId().getName().startsWith("In Progress")).count();
        projectDTO.completedCount = projectTasks.stream().filter(t -> t.getStatusId().getName().startsWith("Completed")).count();

        projectDTO.assignedPercent = (int) ((double) projectDTO.assignedCount * 100 / projectDTO.total);
        projectDTO.inProgressPercent = (int) ((double) projectDTO.inProgressCount * 100 / projectDTO.total);
        projectDTO.completedPercent = (int) ((double) projectDTO.completedCount * 100 / projectDTO.total);

        Optional<ProjectRole> role = roles.stream().filter(r -> r.getRoleId().getName().startsWith("Manager")).findFirst();
        role.ifPresent(projectRole -> projectDTO.managerName = projectRole.getUserId().getName() + " " + projectRole.getUserId().getSecondName());

        projectDTO.employeeCount = roles.stream().filter(r -> r.getProjectId().getId().equals(project.getId())).count();
        return projectDTO;
    }
}
