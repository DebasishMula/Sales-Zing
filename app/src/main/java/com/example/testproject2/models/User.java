package com.example.testproject2.models;

public class User {
    private String EmailId,Password;

    public User(String emailId, String password) {
        EmailId = emailId;
        Password = password;
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
