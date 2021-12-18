package com.example.warehouse.modelclass;

public class Notification_ModelClass {

    String title,Description,userId,id,userType,dateTime;

    public Notification_ModelClass(String title, String description, String userId, String id,
                                   String userType, String dateTime) {
        this.title = title;
        Description = description;
        this.userId = userId;
        this.id = id;
        this.userType = userType;
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
