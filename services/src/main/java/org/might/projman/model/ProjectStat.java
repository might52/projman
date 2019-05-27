package org.might.projman.model;

public class ProjectStat {

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

    public ProjectStat(Long id, String name, String description, int total, long assignedCount, long inProgressCount, long completedCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.total = total;
        this.assignedCount = assignedCount;
        this.inProgressCount = inProgressCount;
        this.completedCount = completedCount;

        assignedPercent = (int) ((double) assignedCount * 100 / total);
        inProgressPercent = (int) ((double) inProgressCount * 100 / total);
        completedPercent = (int) ((double) completedCount * 100 / total);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getAssignedCount() {
        return assignedCount;
    }

    public void setAssignedCount(int assignedCount) {
        this.assignedCount = assignedCount;
    }

    public long getInProgressCount() {
        return inProgressCount;
    }

    public void setInProgressCount(int inProgressCount) {
        this.inProgressCount = inProgressCount;
    }

    public long getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public int getAssignedPercent() {
        return assignedPercent;
    }

    public long getInProgressPercent() {
        return inProgressPercent;
    }

    public long getCompletedPercent() {
        return completedPercent;
    }
}
