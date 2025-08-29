package com.reyson.spotd.Class.Activity.Posts.Comment.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Request {

    @SerializedName("uid")
    @Expose
    var uid: String? = null

    @SerializedName("user")
    @Expose
    var user: String? = null

    override fun toString(): String {
        return "User(uid=$uid,user=$user)"
    }
}