package com.reyson.spotd.Data.CountryCode

import com.reyson.spotd.Data.CountryCode.Model.FirstResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface FirstCodeApi {
    @GET("getip")
    fun getCountryIP(): Observable<FirstResponse>
}