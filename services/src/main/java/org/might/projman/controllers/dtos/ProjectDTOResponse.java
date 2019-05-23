package org.might.projman.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.might.projman.dba.model.Project;

import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProjectDTOResponse implements Serializable {

    private Long id;
    private String name;
    private String description;

    @JsonIgnore
    public static ProjectDTOResponse createFromEntity(Project project) {
        ProjectDTOResponse projectDTO = new ProjectDTOResponse();
        projectDTO.id = project.getId();
        projectDTO.name = project.getName();
        projectDTO.description = project.getDescription();
        return projectDTO;
    }
}
