package org.might.projman.model;

import java.util.Date;

public class CreateEditTaskViewModel extends BaseEntityViewModel {

    private Long projectID;
    private String deadline;

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline.toString();
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }
}
