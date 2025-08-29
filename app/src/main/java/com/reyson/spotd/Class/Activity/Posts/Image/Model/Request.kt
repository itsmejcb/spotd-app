package com.reyson.spotd.Class.Activity.Posts.Image.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Request {

    @SerializedName("uid")
    @Expose
    var uid: String? = null

    @SerializedName("push_key")
    @Expose
    var push_key: String? = null

    override fun toString(): String {
        return "User(uid=$uid,pushKey=$push_key)"
    }
}