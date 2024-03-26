package com.example.a491
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("listings/available")
    suspend fun getAllListedItemData(): List<ListedItemData>
    @GET("listings/by-user/{userId}")
    suspend fun getListedItemData(@Path("userId") userId: Int): List<ListedItemData>

    @GET("listings/others/{userId}/")
    suspend fun getOtherListedItemData(@Path("userId") userId: Int): List<ListedItemData>

    @GET("rentals/current/{userId}")
    suspend fun getCurrentRentItemData(@Path("userId") userId: Int): List<RentItemData>

    @GET("rentals/previous/{userId}")
    suspend fun getPreviousRentItemData(@Path("userId") userId: Int): List<RentItemData>

    @POST("listings/")
    fun createListing(@Body createListing: Listing): Call<Void>

    @POST("rentals/")
    fun createRental(@Body createRental: Rental): Call<Void>

    @GET("users/")
    suspend fun getUsers(): List<Account>

    @POST("users/")
    suspend fun postUser(@Body userData: Account)

    @POST("check-password/")
    suspend fun checkPassword(@Body credentials: Account): ReturnMessage

    @PUT("listings/{pk}/")
    fun updateListing(@Path("pk") listing_id: String?, @Body listing: Listing): Call<Void>

    @POST("listings/make-available/{pk}/")
    suspend fun makeListingAvailable(@Path("pk") listing_id: String?)

    @POST("listings/make-unavailable/{pk}/")
    suspend fun makeListingUnavailable(@Path("pk") listing_id: String?)

    @POST("returns/")
    suspend fun createReturn(@Body returnItem: Return)

    @POST("rentals/make-returned/{pk}/")
    suspend fun makeRentalReturned(@Path("pk") rental_id: String?)
}