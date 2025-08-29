package com.reyson.spotd.Class.Activity.SignUp.FullName.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Request {

    @SerializedName("uid")
    @Expose
    var uid: String? = null

    @SerializedName("first_name")
    @Expose
    var first_name: String? = null

    @SerializedName("middle_name")
    @Expose
    var middle_name: String? = null

    @SerializedName("middle_name")
    @Expose
    var last_name: String? = null

    override fun toString(): String {
        return "User(uid=$uid, first_name=$first_name, middle_name=$middle_name, last_name=$last_name)"
    }
}