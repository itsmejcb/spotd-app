package com.reyson.spotd.Class.Activity.Create.Upload.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Request {
    @SerializedName("uid")
    @Expose
    var uid: String? = null

    @SerializedName("imageName")
    @Expose
    var imageName: String? = null

    @SerializedName("imageStatus")
    @Expose
    var imageStatus: String? = null

    @SerializedName("caption")
    @Expose
    var caption: String? = null

    @SerializedName("timestamp")
    @Expose
    var timestamp: String? = null

    @SerializedName("commentStats")
    @Expose
    var commentStats: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    override fun toString(): String {
        return "User(uid=$uid, imageName=$imageName, imageStatus=$imageStatus, caption=$caption, ms=$timestamp, commentStats=$commentStats, status=$status)"
    }
}