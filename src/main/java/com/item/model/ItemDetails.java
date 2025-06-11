package com.item.model;

public class ItemDetails {

    private int id;
    private String description;
    private String brand;
    private String category;
    private int itemId;  

   
    public ItemDetails() {}

   
    public ItemDetails(String description, String brand, String category, int itemId) {
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.itemId = itemId;
    }

    
    public ItemDetails(int id, String description, String brand, String category, int itemId) {
        this.id = id;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.itemId = itemId;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
