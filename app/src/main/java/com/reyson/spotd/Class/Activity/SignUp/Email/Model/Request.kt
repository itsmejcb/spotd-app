package com.reyson.spotd.Class.Activity.SignUp.Email.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Request {
    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("uuid")
    @Expose
    var uuid: String? = null

    @SerializedName("code")
    @Expose
    var code: String? = null

    override fun toString(): String {
        return "User(uuid=$uuid,email=$email, password=$password, code=$code)"
    }
}