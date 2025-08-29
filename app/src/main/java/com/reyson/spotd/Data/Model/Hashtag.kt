package com.reyson.spotd.Data.Model

class Hashtag private constructor() {
    @JvmField
    var hashtag: String? = null

    fun reset() {
        hashtag = null
    }

    companion object {
        @JvmStatic
        @get:Synchronized
        var instance: Hashtag? = null
            get() {
                if (field == null) {
                    field = Hashtag()
                }
                return field
            }
    }
}