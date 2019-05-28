package org.might.projman;

public class UserPreference {

    private long userID;

    private String userLogin;

    private boolean admin;
    private boolean manager;

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public long getUserID() {
        return userID;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }
}
