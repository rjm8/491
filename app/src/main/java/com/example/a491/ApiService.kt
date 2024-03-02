package com.example.a491
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("listings/available")
    suspend fun getAllListedItemData(): List<ListedItemData>
    @GET("listings/available/{userId}")
    suspend fun getListedItemData(@Path("userId") userId: Int): List<ListedItemData>

    @GET("rentals/current/{userId}")
    suspend fun getCurrentRentItemData(@Path("userId") userId: Int): List<RentItemData>

    @GET("rentals/previous/{userId}")
    suspend fun getPreviousRentItemData(@Path("userId") userId: Int): List<RentItemData>

    @POST("rapidrentals/listings/")
    fun createListing(@Body createListing: Listing): Call<Void>

    @POST("rapidrentals/rentals/")
    fun createRental(@Body createRental: Rental): Call<Void>
}