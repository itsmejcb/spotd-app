package com.reyson.spotd.Class.Fragments.Home.Foryou.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Request {
    @SerializedName("uid")
    @Expose
    var uid: String? = null

    override fun toString(): String {
        return "User(uid=$uid)"
    }
}