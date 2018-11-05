package com.example.jeffe.trabalho_final;

import com.example.jeffe.trabalho_final.Build.Item;

import java.util.List;

public class Usuario {
    private String userId;
    private List<Item> userBuild;
    private List<String> friendsList;
    private String userName;
    private String userDescription;
    private String userPicture;

    public Usuario(String userId,String userName,String userDescription){
        this.userId = userId;
        this.userName = userName;
        this.userDescription = userDescription;

    }
    public Usuario(String userId, List<Item> userBuild, List<String> friendsList, String userName, String userDescription) {
        this.userId = userId;
        this.userBuild = userBuild;
        this.friendsList = friendsList;
        this.userName = userName;
        this.userDescription = userDescription;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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



}
