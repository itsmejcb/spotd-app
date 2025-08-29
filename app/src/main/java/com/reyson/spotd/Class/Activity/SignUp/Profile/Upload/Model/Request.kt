package com.reyson.spotd.Class.Activity.SignUp.Profile.Upload.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Request {
    @SerializedName("uid")
    @Expose
    var uid: String? = null

    @SerializedName("filename")
    @Expose
    var filename: String? = null

    @SerializedName("extension")
    @Expose
    var extension: String? = null

    override fun toString(): String {
        return "User(uid=$uid, ms=$filename, extension=$extension)"
    }
}