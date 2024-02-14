package com.example.a491
//import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class Item (
    @SerializedName("itemTitle")
    val itemTitle: String? = null,

    @SerializedName("itemPrice")
    val itemPrice: String? = null,

    @SerializedName("itemDesc")
    val itemDesc: String? = null,

    @SerializedName("itemImage")
    var itemImageUrl: String? = null
) : java.io.Serializable {
}