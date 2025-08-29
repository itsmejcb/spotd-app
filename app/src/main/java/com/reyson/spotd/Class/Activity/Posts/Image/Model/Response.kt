package com.reyson.spotd.Class.Activity.Posts.Image.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reyson.spotd.Data.Model.Posts

class Response {
    @SerializedName("posts")
    @Expose
    var posts: ArrayList<Posts>? = null

    override fun toString(): String {
        return "$posts"
    }

    // @SerializedName("status")
    // @Expose
    // var status: String? = null
    //
    // @SerializedName("uid")
    // @Expose
    // var uid: String? = null
    //
    // @SerializedName("username")
    // @Expose
    // var username: String? = null
    //
    // @SerializedName("full_name")
    // @Expose
    // var full_name: String? = null
    //
    // @SerializedName("profile_picture")
    // @Expose
    // var profile_picture: String? = null
    //
    // @SerializedName("verified")
    // @Expose
    // var verified: String? = null
    //
    // @SerializedName("posts")
    // @Expose
    // var posts: ArrayList<Posts>? = null
    //
    // override fun toString(): String {
    //     return "Response(status=$status, uid=$uid,username=$username,full_name=$full_name,profile_picture=$profile_picture,verify=$verified,posts=$posts)"
    // }
}