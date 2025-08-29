package com.reyson.spotd.Class.Activity.Posts.Comment.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Response {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("uid")
    @Expose
    var uid: String? = null

    @SerializedName("username")
    @Expose
    var username: String? = null

    @SerializedName("full_name")
    @Expose
    var full_name: String? = null

    @SerializedName("profile_picture")
    @Expose
    var profile_picture: String? = null

    @SerializedName("verified")
    @Expose
    var verified: String? = null

    @SerializedName("bio")
    @Expose
    var bio: String? = null

    @SerializedName("bio_status")
    @Expose
    var bio_status: String? = null

    @SerializedName("total_post")
    @Expose
    var total_post: String? = null

    @SerializedName("total_following")
    @Expose
    var total_following: String? = null

    @SerializedName("total_follower")
    @Expose
    var total_follower: String? = null

    @SerializedName("is_following")
    @Expose
    var is_following: String? = null

    @SerializedName("is_follower")
    @Expose
    var is_follower: String? = null

    override fun toString(): String {
        return "Response(status=$status, uid=$uid,username=$username,full_name=$full_name,profile_picture=$profile_picture,verify=$verified,bio=$bio,bio_status=$bio_status,total_post=$total_post,total_following=$total_following,total_follower=$total_follower,is_following=$is_following,is_follower=$is_follower)"
    }
}