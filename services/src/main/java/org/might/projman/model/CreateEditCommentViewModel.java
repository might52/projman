package org.might.projman.model;

import org.might.projman.dba.model.User;
import org.springframework.lang.NonNull;

import java.util.Date;

public class CreateEditCommentViewModel extends BaseEntityViewModel {

    @NonNull
    private User createdBy;

    @NonNull
    private Date creationDate;

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
}
