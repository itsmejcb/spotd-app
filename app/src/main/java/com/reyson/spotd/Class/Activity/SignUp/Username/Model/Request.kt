package com.reyson.spotd.Class.Activity.SignUp.Username.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Request {
    @SerializedName("uid")
    @Expose
    var uid: String? = null

    @SerializedName("password")
    @Expose
    var username: String? = null

    override fun toString(): String {
        return "User(uid=$uid, username=$username)"
    }
}