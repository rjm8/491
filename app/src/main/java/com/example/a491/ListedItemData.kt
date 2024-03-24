package com.example.a491

data class ListedItemData(
    val listing_id: Int,
    val rental_price_per_day: String,
    val retail_price: String,
    val item_name: String,
    val image_url: String,
    val location: String,
    val description: String,
    val available: Boolean,
    val max_duration: String,
    val lister: Int
)
