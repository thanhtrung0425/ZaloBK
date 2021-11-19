package com.example.zalobk.models;

public class UserProfile {

    private String UserName;
    private String UserID;

    public UserProfile() {
    }

    public UserProfile(String userName, String userID) {
        UserName = userName;
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
