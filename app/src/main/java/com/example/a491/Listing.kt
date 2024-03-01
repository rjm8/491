package com.example.a491

data class Listing(
    val rental_price_per_day: String,
    val retail_price: String,
    val item_name: String,
    val image_url: String,
    val description: String,
    val max_duration: Int,
    val lister: Int
)
