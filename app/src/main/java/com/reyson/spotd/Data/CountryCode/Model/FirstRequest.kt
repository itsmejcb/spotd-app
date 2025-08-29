package com.reyson.spotd.Data.CountryCode.Model

import com.google.gson.annotations.SerializedName

data class FirstRequest(
    @SerializedName("ip")
    val ip: String
)