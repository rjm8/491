package com.example.a491

import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Body

interface ListingService {
    @POST("rapidrentals/listings")
    fun createListing(@Body createListing: Listing): Call<Void>
}