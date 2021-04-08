package com.company;

import java.security.Timestamp;


public class Note {
    private int id;
    private String title;
    private String description;
    private String lastUpdate;
    private String imageUrl;
    private String fileUrl;


    public Note() {
    }

    public Note(int id, String title, String description, String lastUpdate, String imageUrl, String fileUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.lastUpdate = lastUpdate;
        this.imageUrl = imageUrl;
        this.fileUrl = fileUrl;
    }

    public Note(String title, String description, String lastUpdate, String imageUrl, String fileUrl) {
        this.title = title;
        this.description = description;
        this.lastUpdate = lastUpdate;
        this.imageUrl = imageUrl;
        this.fileUrl = fileUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}

