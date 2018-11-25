package com.example.jeffe.trabalho_final;

import com.example.jeffe.trabalho_final.Build.Item;

import java.util.List;

public class Usuario {
    private String userId;
    private List<Item> userBuild;
    private List<String> friendsList;
    private String userName;
    private String userEmail;
    private String userDescription;
    private String userPicture;
    private String userLocalization;

    public Usuario(String userId, String userName, String userEmail, String userLocalization) {
        this.userId = userId;
        this.userBuild = null;
        this.friendsList = null;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userDescription =  null;
        this.userLocalization = userLocalization;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<Item> getUserBuild() {
        return userBuild;
    }

    public void setUserBuild(List<Item> userBuild) {
        this.userBuild = userBuild;
    }

    public List<String> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<String> friendsList) {
        this.friendsList = friendsList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getUserLocalization() {
        return userLocalization;
    }

    public void setUserLocalization(String userLocalization) {
        this.userLocalization = userLocalization;
    }

}
