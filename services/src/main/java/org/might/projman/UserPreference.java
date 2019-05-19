package org.might.projman;

public class UserPreference {

    private long userID;

    private String userLogin;

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
}
