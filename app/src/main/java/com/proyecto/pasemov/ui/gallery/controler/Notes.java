package com.proyecto.pasemov.ui.gallery.controler;

public class Notes extends RealmObject{
    String title, descriptionn;
    long createdTime;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptionn() {
        return descriptionn;
    }

    public void setDescriptionn(String descriptionn) {
        this.descriptionn = descriptionn;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}
