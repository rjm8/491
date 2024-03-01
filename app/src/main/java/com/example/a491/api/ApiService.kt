package com.example.a491.api
import com.example.a491.Account
import com.example.a491.ReturnMessage
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("users/")
    suspend fun getUsers(): List<Account>

    @POST("users/")
    suspend fun postUser(@Body userData: Account)

    @POST("check-password/")
    suspend fun checkPassword(@Body credentials: Account): ReturnMessage
}
