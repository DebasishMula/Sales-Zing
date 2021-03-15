package com.example.testproject2.models;

public class LogInResult {


private  String token,email;

    public LogInResult(String token, String email) {

        this.token = token;
        this.email = email;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
