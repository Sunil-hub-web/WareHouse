package com.example.warehouse.modelclass;

public class HouseholdItemsWeight_ModelClass {

    String weight;

    public HouseholdItemsWeight_ModelClass(String weight) {
        this.weight = weight;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "ProductWeight_ModelClass{" +
                "weight='" + weight + '\'' +
                '}';
    }
}
