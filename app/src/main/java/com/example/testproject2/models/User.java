package com.example.testproject2.models;

public class User {
    private String EmailId,Password, token,deviceId,userId,branchName,branchid;

    public User( String emailId,String password) {
        EmailId = emailId;
        this.Password = password;
    }

    public User(String emailId, String password, String token) {
        EmailId = emailId;
        Password = password;
        this.token = token;
    }

    public User(String emailId, String password, String token, String deviceId, String userId, String branchName,String branchid) {
        EmailId = emailId;
        Password = password;
        this.token = token;
        this.deviceId = deviceId;
        this.userId = userId;
        this.branchName = branchName;
        this.branchid=branchid;
    }
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }
}