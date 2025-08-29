package com.reyson.spotd.Class.Activity.SignUp.Email.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Response {
    @SerializedName("uid")
    @Expose
    var uid: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    override fun toString(): String {
        return "Response(uid=$uid, status=$status, message=$message)"
    }
}