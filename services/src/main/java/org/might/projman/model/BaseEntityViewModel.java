package org.might.projman.model;

import org.springframework.lang.Nullable;

abstract public class BaseEntityViewModel {

    @Nullable
    private Long id;
    @Nullable
    private String name;
    @Nullable
    private String description;

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }
}
