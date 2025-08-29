package com.reyson.spotd.Data.CountryCode.Model

import com.google.gson.annotations.SerializedName

data class SecondResponse(
    @SerializedName("country")
    val countryCode: String, // Assuming the field name is 'countryCode' in the response
    @SerializedName("region")
    val regionCode: String, // Assuming the field name is 'countryCode' in the response
    @SerializedName("city")
    val cityCode: String // Assuming the field name is 'countryCode' in the response
)