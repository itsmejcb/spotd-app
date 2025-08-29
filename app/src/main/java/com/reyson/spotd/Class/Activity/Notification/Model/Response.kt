package com.reyson.spotd.Class.Activity.Notification.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reyson.spotd.Data.Model.Notify

class Response {
    @SerializedName("notify")
    @Expose
    var notify: ArrayList<Notify>? = null

    override fun toString(): String {
        return "$notify"
    }
}