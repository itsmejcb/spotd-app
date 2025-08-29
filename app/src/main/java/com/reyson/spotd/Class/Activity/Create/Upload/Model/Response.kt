package com.reyson.spotd.Class.Activity.Create.Upload.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Response {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("push_key")
    @Expose
    var push_key: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    override fun toString(): String {
        return "Response(status=$status, push_key=$push_key, message=$message)"
    }
}