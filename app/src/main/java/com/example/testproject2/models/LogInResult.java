package com.example.testproject2.models;
import java.io.Serializable;

@SuppressWarnings("serial")
public class LogInResult implements   Serializable{


    private  String status_code,status_message,msgstr,branchname,authtoken,userid,branchid;


    public LogInResult(String status_code, String status_message, String msgstr, String branchname, String authtoken, String userid,String branchid) {
        this.status_code = status_code;
        this.status_message = status_message;
        this.msgstr = msgstr;
        this.branchname = branchname;
        this.authtoken = authtoken;
        this.userid = userid;
        this.branchid=branchid;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public String getMsgstr() {
        return msgstr;
    }

    public void setMsgstr(String msgstr) {
        this.msgstr = msgstr;
    }



    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    @Override
    public String toString() {
        return "LogInResult{" +
                "status_code='" + status_code + '\'' +
                ", status_message='" + status_message + '\'' +
                ", msgstr='" + msgstr + '\'' +
                ", branchname='" + branchname + '\'' +
                ", authtoken='" + authtoken + '\'' +
                ", userid='" + userid + '\'' +
                ", branchid='" + branchid + '\'' +
                '}';
    }
}