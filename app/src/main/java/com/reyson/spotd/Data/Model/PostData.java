package com.reyson.spotd.Data.Model;

import java.util.ArrayList;

public class PostData {
    private static PostData instance;
    private String uid;
    private String username;
    private String fullName;
    private String profile;
    private String bio;
    private String bioStatus;
    private String totalPost;
    private String totalFollowing;
    private String totalFollower;
    private String verify;
    private String isFollowing;
    private String isFollower;
    private String auth;
    private String uuid;
    private String status;
    private ArrayList<Posts> posts;

    private PostData() {
        this.uid = null;
        this.status = null;
        this.username = null;
        this.fullName = null;
        this.profile = null;
        this.bio = null;
        this.bioStatus = null;
        this.totalPost = null;
        this.totalFollowing = null;
        this.totalFollower = null;
        this.verify = null;
        this.isFollowing = null;
        this.isFollower = null;
        this.auth = auth;
        this.uuid = uuid;
        this.posts = new ArrayList<Posts>();
    }
    public void reset() {
        this.uid = null;
        this.status = null;
        this.username = null;
        this.fullName = null;
        this.profile = null;
        this.bio = null;
        this.bioStatus = null;
        this.totalPost = null;
        this.totalFollowing = null;
        this.totalFollower = null;
        this.verify = null;
        this.isFollowing = null;
        this.isFollower = null;
        this.auth = null;
        this.uuid = null;
        this.posts = new ArrayList<Posts>();
    }

    public static synchronized PostData getInstance() {
        if (instance == null) {
            instance = new PostData();
        }
        return instance;
    }

    public static void setInstance(PostData instance) {
        PostData.instance = instance;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBioStatus() {
        return bioStatus;
    }

    public void setBioStatus(String bioStatus) {
        this.bioStatus = bioStatus;
    }

    public String getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(String totalPost) {
        this.totalPost = totalPost;
    }

    public String getTotalFollowing() {
        return totalFollowing;
    }

    public void setTotalFollowing(String totalFollowing) {
        this.totalFollowing = totalFollowing;
    }

    public String getTotalFollower() {
        return totalFollower;
    }

    public void setTotalFollower(String totalFollower) {
        this.totalFollower = totalFollower;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(String isFollowing) {
        this.isFollowing = isFollowing;
    }

    public String getIsFollower() {
        return isFollower;
    }

    public void setIsFollower(String isFollower) {
        this.isFollower = isFollower;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ArrayList<Posts> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Posts> posts) {
        this.posts = posts;
    }
}
