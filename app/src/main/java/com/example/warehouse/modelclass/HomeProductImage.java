package com.example.warehouse.modelclass;

public class HomeProductImage {

    String images;

    public HomeProductImage(String images) {
        this.images = images;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "ProductImage_ModelClass{" +
                "images='" + images + '\'' +
                '}';
    }
}
