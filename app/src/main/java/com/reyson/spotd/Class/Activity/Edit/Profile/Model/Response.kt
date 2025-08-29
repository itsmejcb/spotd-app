package com.reyson.spotd.Class.Activity.Edit.Profile.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Response {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    override fun toString(): String {
        return "Response(status=$status, message=$message)"
    }
}