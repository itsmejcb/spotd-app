package com.reyson.spotd.Data.Model;

public class PhotosData {
    private static PhotosData instance;
    private String uid;
    private String push_key;


    private PhotosData() {
        this.uid = null;
        this.push_key = null;
    }
    public void reset() {
        this.uid = null;
        this.push_key = null;
    }
    public static synchronized PhotosData getInstance() {
        if (instance == null) {
            instance = new PhotosData();
        }
        return instance;
    }

    public static void setInstance(PhotosData instance) {
        PhotosData.instance = instance;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPush_key() {
        return push_key;
    }

    public void setPush_key(String push_key) {
        this.push_key = push_key;
    }
}
