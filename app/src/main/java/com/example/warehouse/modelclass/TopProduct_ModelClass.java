package com.example.warehouse.modelclass;

import java.util.ArrayList;

public class TopProduct_ModelClass {

    String name,url;
    ArrayList<Data_ModelClass> data;

    public TopProduct_ModelClass(String name, String url, ArrayList<Data_ModelClass> data) {
        this.name = name;
        this.url = url;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<Data_ModelClass> getData() {
        return data;
    }

    public void setData(ArrayList<Data_ModelClass> data) {
        this.data = data;
    }
}
