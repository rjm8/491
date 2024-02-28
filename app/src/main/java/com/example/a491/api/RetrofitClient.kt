package com.example.a491.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val instance = Retrofit.Builder()
        .baseUrl("https://rapidrentals-9797a640fd53.herokuapp.com/rapidrentals/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}