package com.reyson.spotd.Class.Activity.SignUp.FullName.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Response {
    @SerializedName("status")
    @Expose
    var status: String? = null

    override fun toString(): String {
        return "Response(status=$status)"
    }
}