package com.reyson.spotd.Data.CountryCode

import com.reyson.spotd.Data.CountryCode.Model.SecondResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface SecondCodeApi {
    @GET("{ip}/json")
    fun getCountryDetails(@Path("ip") ipAddress: String): Observable<SecondResponse>
}