package com.example.ims_sec.Model;

public class ItemModel {
    private String ItemName;
    private String ItemNumber;
    private String ItemType;
    private String imageUrl;

    public ItemModel() {

    }

    public ItemModel(String itemName, String itemNumber, String itemType, String imageUrl) {
        ItemName = itemName;
        ItemNumber = itemNumber;
        ItemType = itemType;
        this.imageUrl = imageUrl;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemNumber() {
        return ItemNumber;
    }

    public void setItemNumber(String itemNumber) {
        ItemNumber = itemNumber;
    }

    public String getItemType() {
        return ItemType;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
