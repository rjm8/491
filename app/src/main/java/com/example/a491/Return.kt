package com.example.a491

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class Return (
    @SerializedName("rental")
    val rental_id: Int?,

    @SerializedName("tip_amount")
    val tip_amount: String?,

    @SerializedName("location_of_renter")
    val location_of_renter: String?,

    @SerializedName("location_of_lister")
    val location_of_lister: String?,

    @SerializedName("return_date")
    val return_date: String?
) : java.io.Serializable {
}