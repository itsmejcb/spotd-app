package com.reyson.spotd.Data.Model;

public class Notify {
    private String uid;
    private String push_key;
    private String ms;
    private String status;
    private String context;
    private String username;
    private String profile_picture;
    private String full_name;
    private String follower;
    private String following;
    private String image;


    public Notify() {
        this.uid = uid;
        this.push_key = push_key;
        this.ms = ms;
        this.status = status;
        this.context = context;
        this.username = username;
        this.profile_picture = profile_picture;
        this.full_name = full_name;
        this.following = following;
        this.follower = follower;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFollower() {
        return follower;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public void setFollower(String follower) {
        this.follower = follower;
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

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
