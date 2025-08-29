package com.reyson.spotd.Class.Fragments.Profile.Post.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.reyson.spotd.Data.Model.Posts

class Response {
    @SerializedName("posts")
    @Expose
    var posts: ArrayList<Posts>? = null

    override fun toString(): String {
        return "$posts"
    }
}