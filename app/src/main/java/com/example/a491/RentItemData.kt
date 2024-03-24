package com.example.a491

data class RentItemData(
    val rental_id: Int,
    val lister_location: String,
    val renter_location: String,
    val tip_amount_for_driver: String,
    val rental_date: String,
    val total_price: String,
    val returned: Boolean,
    val delivered: Boolean,
    val notify_lister_delivered_safely: Boolean,
    val duration: Int,
    val item_name: String,
    val image_url: String,
    val description: String,
    val lister: Int,
    val renter: Int,
    val listing: Int,
    val driver: String
)
