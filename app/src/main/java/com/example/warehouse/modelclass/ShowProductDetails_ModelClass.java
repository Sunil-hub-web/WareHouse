package com.example.warehouse.modelclass;

import java.util.ArrayList;

public class ShowProductDetails_ModelClass {

    String discount,title,price;
    ArrayList<ProductImage_ModelClass> productimage;
    ArrayList<ProductWeight_ModelClass> productweight;

    public ShowProductDetails_ModelClass(String discount, String title, String price, ArrayList<ProductImage_ModelClass> productimage,
                                         ArrayList<ProductWeight_ModelClass> productweight) {
        this.discount = discount;
        this.title = title;
        this.price = price;
        this.productimage = productimage;
        this.productweight = productweight;
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

    public ArrayList<ProductImage_ModelClass> getProductimage() {
        return productimage;
    }

    public void setProductimage(ArrayList<ProductImage_ModelClass> productimage) {
        this.productimage = productimage;
    }

    public ArrayList<ProductWeight_ModelClass> getProductweight() {
        return productweight;
    }

    public void setProductweight(ArrayList<ProductWeight_ModelClass> productweight) {
        this.productweight = productweight;
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
