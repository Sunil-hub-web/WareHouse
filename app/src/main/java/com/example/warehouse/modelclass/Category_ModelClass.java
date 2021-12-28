package com.example.warehouse.modelclass;

public class Category_ModelClass {

    String categoryId,categoryName,productTyoe;

    public Category_ModelClass(String categoryId, String categoryName, String productTyoe) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.productTyoe = productTyoe;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductTyoe() {
        return productTyoe;
    }

    public void setProductTyoe(String productTyoe) {
        this.productTyoe = productTyoe;
    }
}
