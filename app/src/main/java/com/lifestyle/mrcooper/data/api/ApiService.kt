package com.lifestyle.mrcooper.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("Phone/Generate")
    suspend fun getPhoneNumbers(
        @Query("CountryCode") countryCode: String = "IN",
        @Query("Quantity") quantity: Int = 1
    ): List<String>
}