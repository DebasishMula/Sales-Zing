package com.example.testproject2.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ResponseSms {

    String status_code,status_message,msgstr;

    public ResponseSms(String status_code, String status_message, String msgstr) {
        this.status_code = status_code;
        this.status_message = status_message;
        this.msgstr = msgstr;
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

    @Override
    public String toString() {
        return "Response{" +
                "status_code='" + status_code + '\'' +
                ", status_message='" + status_message + '\'' +
                ", msgstr='" + msgstr + '\'' +
                '}';
    }
}