package com.example.ims_sec.Model;

public class ContactModel {
    private String Name;
    private String Phone;
    private String UserId;


    public ContactModel(String name, String phone, String userId) {
        Name = name;
        Phone = phone;
        UserId = userId;
    }

    public ContactModel() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
