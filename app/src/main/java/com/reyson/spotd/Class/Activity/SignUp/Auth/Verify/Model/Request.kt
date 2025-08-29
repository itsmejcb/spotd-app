package com.reyson.spotd.Class.Activity.SignUp.Auth.Verify.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Request {
    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    override fun toString(): String {
        return "User(email=$email, password=$password)"
    }
}