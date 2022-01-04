package com.example.warehouse.modelclass;

import java.util.ArrayList;

public class Vegetables_Modelclass {

    String discount,id,title,price,type,description,totalRating,favid;
    ArrayList<Image_ModelClass> image_modelClasses;
    ArrayList<Weight_ModelClass> weight_modelClasses;

    public Vegetables_Modelclass(String discount, String id, String title, String price, String type, String description,
                                 ArrayList<Image_ModelClass> image_modelClasses, ArrayList<Weight_ModelClass> weight_modelClasses,
                                 String totalRating,String favid) {
        this.discount = discount;
        this.id = id;
        this.title = title;
        this.price = price;
        this.type = type;
        this.description = description;
        this.image_modelClasses = image_modelClasses;
        this.weight_modelClasses = weight_modelClasses;
        this.totalRating = totalRating;
        this.favid = favid;
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

    public ArrayList<Image_ModelClass> getImage_modelClasses() {
        return image_modelClasses;
    }

    public void setImage_modelClasses(ArrayList<Image_ModelClass> image_modelClasses) {
        this.image_modelClasses = image_modelClasses;
    }

    public ArrayList<Weight_ModelClass> getWeight_modelClasses() {
        return weight_modelClasses;
    }

    public void setWeight_modelClasses(ArrayList<Weight_ModelClass> weight_modelClasses) {
        this.weight_modelClasses = weight_modelClasses;
    }

    public String getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }

    public String getFavid() {
        return favid;
    }

    public void setFavid(String favid) {
        this.favid = favid;
    }
}
