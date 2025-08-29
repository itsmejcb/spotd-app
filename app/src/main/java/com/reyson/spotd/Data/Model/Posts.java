package com.reyson.spotd.Data.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Posts {
    private String uid;
    private String username;
    private String full_name;
    private String profile_picture;
    private String verified;
    private String push_key;
    private ArrayList<SubImage> images;
    private String image;
    private String caption;
    private String ms;
    private String status;
    private String edit_status;
    private String comment_status;
    private String total_views;
    private String share_status;
    private String repost_status;
    private String total_likes;
    private String total_comments;
    private String total_share;
    private String total_repost;
    //private String likes;
    private String userPhotos;
    private String isLiked;
    private String censored;
    private String total_count;


    public Posts() {
        this.uid = uid;
        this.username = username;
        this.full_name = full_name;
        this.profile_picture = profile_picture;
        this.verified = verified;
        this.push_key = push_key;
        this.image = image;
        this.images = images;
        this.caption = caption;
        this.ms = ms;
        this.status = status;
        this.edit_status = edit_status;
        this.comment_status = comment_status;
        this.total_views = total_views;
        this.share_status = share_status;
        this.repost_status = repost_status;
        this.total_likes = total_likes;
        this.total_comments = total_comments;
        this.total_share = total_share;
        this.total_repost = total_repost;
        //this.likes = likes;
        this.userPhotos = userPhotos;
        this.isLiked = isLiked;
        this.censored = censored;
        this.total_count = total_count;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getPush_key() {
        return push_key;
    }

    public void setPush_key(String push_key) {
        this.push_key = push_key;
    }

    //public String getImages() {
    //    return images;
    //}
    //
    //public void setImages(String images) {
    //    this.images = images;
    //}


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<SubImage> getImages() {
        return images;
    }

    public void setImages(JSONArray interests) {
        ArrayList<SubImage> subLists = new ArrayList<>();
        for (int i = 0; i < interests.length(); i++) {
            try {
                JSONObject jsonObject = interests.getJSONObject(i);
                SubImage subList = new SubImage();
                subList.setImage(jsonObject.getString("image"));
                subLists.add(subList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.images = subLists;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String context) {
        this.caption = context;
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

    public String getEdit_status() {
        return edit_status;
    }

    public void setEdit_status(String edit_status) {
        this.edit_status = edit_status;
    }

    public String getComment_status() {
        return comment_status;
    }

    public void setComment_status(String comment_status) {
        this.comment_status = comment_status;
    }

    public String getTotal_views() {
        return total_views;
    }

    public void setTotal_views(String total_views) {
        this.total_views = total_views;
    }

    public String getShare_status() {
        return share_status;
    }

    public void setShare_status(String share_status) {
        this.share_status = share_status;
    }

    public String getRepost_status() {
        return repost_status;
    }

    public void setRepost_status(String repost_status) {
        this.repost_status = repost_status;
    }

    public String getTotal_likes() {
        return total_likes;
    }

    public void setTotal_likes(String total_likes) {
        this.total_likes = total_likes;
    }

    public String getTotal_comments() {
        return total_comments;
    }

    public void setTotal_comments(String total_comments) {
        this.total_comments = total_comments;
    }

    public String getTotal_share() {
        return total_share;
    }

    public void setTotal_share(String total_share) {
        this.total_share = total_share;
    }

    public String getTotal_repost() {
        return total_repost;
    }

    public void setTotal_repost(String total_repost) {
        this.total_repost = total_repost;
    }

    //public String getLikes() {
    //    return likes;
    //}

    //public void setLikes(String likes) {
    //    this.likes = likes;
    //}

    public String getUserPhotos() {
        return userPhotos;
    }

    public void setUserPhotos(String userPhotos) {
        this.userPhotos = userPhotos;
    }

    public String getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(String isLiked) {
        this.isLiked = isLiked;
    }

    public String getCensored() {
        return censored;
    }

    public void setCensored(String censored) {
        this.censored = censored;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }
}
