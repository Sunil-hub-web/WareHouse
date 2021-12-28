package com.example.warehouse.modelclass;

import java.util.ArrayList;

public class ShowHomeProduct {

    String discount,id,title,price,type,description;
    ArrayList<HomeProductImage> image_modelClasses;
    ArrayList<HomeProductWeight> weight_modelClasses;

    public ShowHomeProduct(String discount, String id, String title, String price, String type, String description,
                           ArrayList<HomeProductImage> image_modelClasses, ArrayList<HomeProductWeight> weight_modelClasses) {
        this.discount = discount;
        this.id = id;
        this.title = title;
        this.price = price;
        this.type = type;
        this.description = description;
        this.image_modelClasses = image_modelClasses;
        this.weight_modelClasses = weight_modelClasses;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<HomeProductImage> getImage_modelClasses() {
        return image_modelClasses;
    }

    public void setImage_modelClasses(ArrayList<HomeProductImage> image_modelClasses) {
        this.image_modelClasses = image_modelClasses;
    }

    public ArrayList<HomeProductWeight> getWeight_modelClasses() {
        return weight_modelClasses;
    }

    public void setWeight_modelClasses(ArrayList<HomeProductWeight> weight_modelClasses) {
        this.weight_modelClasses = weight_modelClasses;
    }

    @Override
    public String toString() {
        return "ShowHomeProduct{" +
                "discount='" + discount + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", image_modelClasses=" + image_modelClasses +
                ", weight_modelClasses=" + weight_modelClasses +
                '}';
    }
}
