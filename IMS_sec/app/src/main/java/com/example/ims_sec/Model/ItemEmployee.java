package com.example.ims_sec.Model;

public class ItemEmployee {

    private String ItemName;
    private String ItemNumber;
    private String ItemType;
    private String imageUrl;


    public ItemEmployee(String ItemName, String ItemNumber, String ItemType, String imageUrl) {
        this.ItemName = ItemName;
        this.ItemNumber = ItemNumber;
        this.ItemType = ItemType;
        this.imageUrl = imageUrl;
    }

    public ItemEmployee() {
    }


    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public String getItemNumber() {
        return ItemNumber;
    }

    public void setItemNumber(String ItemNumber) {
        this.ItemNumber = ItemNumber;
    }

    public String getItemType() {
        return ItemType;
    }

    public void setItemType(String ItemType) {
        this.ItemType = ItemType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
