package com.example.ims_sec.Model;

public class MessageModel {
    private String Message;
    private String Sender;
    private  String TimeandDate;

    public MessageModel() {

    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getTimeandDate() {
        return TimeandDate;
    }

    public void setTimeandDate(String timeandDate) {
        TimeandDate = timeandDate;
    }

    public MessageModel(String message, String sender, String timeandDate) {
        Message = message;
        Sender = sender;
        TimeandDate = timeandDate;
    }
}
