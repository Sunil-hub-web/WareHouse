package com.example.warehouse.modelclass;

public class AddressDetails_ModelClass {

    String House,street,locality,city,state,country,zip,Latitude,Longitude,Default,id,userid;

    public AddressDetails_ModelClass(String house, String street, String locality, String city, String state,
                                     String country, String zip, String Latitude,String Longitude,String Default,
                                     String id,String userid) {
        this.House = house;
        this.street = street;
        this.locality = locality;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zip = zip;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.Default = Default;
        this.id = id;
        this.userid = userid;
    }

    public String getHouse() {
        return House;
    }

    public void setHouse(String house) {
        House = house;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getDefault() {
        return Default;
    }

    public void setDefault(String aDefault) {
        Default = aDefault;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
