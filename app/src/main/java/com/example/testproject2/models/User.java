package com.example.testproject2.models;

public class User {
    private String EmailId,Password, token;

    public User( String emailId,String password) {
        EmailId = emailId;
        this.Password = password;
    }

    public User(String emailId, String password, String token) {
        EmailId = emailId;
        Password = password;
        this.token = token;
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
}
