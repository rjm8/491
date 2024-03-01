package com.example.a491

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RentalService {
    @POST("rapidrentals/rentals/")
    fun createRental(@Body createRental: Rental): Call<Void>
}