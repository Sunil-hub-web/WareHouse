package com.example.warehouse.modelclass;

public class HouseholdItemsImage_ModelClass {

    String images;

    public HouseholdItemsImage_ModelClass(String images) {
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
        return /*"ProductImage_ModelClass{" +
                "images='" +*/ images /*+ '\'' +
                '}'*/;
    }
}
