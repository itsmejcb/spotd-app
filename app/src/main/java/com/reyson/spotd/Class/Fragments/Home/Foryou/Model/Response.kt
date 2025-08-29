package com.reyson.spotd.Class.Fragments.Home.Foryou.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reyson.spotd.Data.Model.Posts

class Response {
    @SerializedName("foryou")
    @Expose
    var foryou: ArrayList<Posts>? = null

    override fun toString(): String {
        return "$foryou"
    }
}