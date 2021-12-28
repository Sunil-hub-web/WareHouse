package com.example.warehouse.modelclass;

import java.util.ArrayList;

public class ShowProductDetails_ModelClass {

    String discount,title,price,id;
    ArrayList<Image_ModelClass> productimage;
    ArrayList<Weight_ModelClass> productweight;

    public ShowProductDetails_ModelClass(String discount, String title, String price, ArrayList<Image_ModelClass> productimage,
                                         ArrayList<Weight_ModelClass> productweight,String id) {
        this.discount = discount;
        this.title = title;
        this.price = price;
        this.productimage = productimage;
        this.productweight = productweight;
        this.id = id;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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

    public ArrayList<Image_ModelClass> getProductimage() {
        return productimage;
    }

    public void setProductimage(ArrayList<Image_ModelClass> productimage) {
        this.productimage = productimage;
    }

    public ArrayList<Weight_ModelClass> getProductweight() {
        return productweight;
    }

    public void setProductweight(ArrayList<Weight_ModelClass> productweight) {
        this.productweight = productweight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ShowProductDetails_ModelClass{" +
                "discount='" + discount + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", productimage=" + productimage +
                ", productweight=" + productweight +
                '}';
    }
}
