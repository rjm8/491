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

    @SerializedName("image_url")
    var itemImageUrl: String? = null,

    @SerializedName("itemRetailPrice")
    var itemRetailPrice: String? = null,

    @SerializedName("itemLocation")
    var itemLocation: String? = null,

    @SerializedName("itemListerLocation")
    val itemListerLocation: String? = null,

    @SerializedName("itemRenterLocation")
    val itemRenterLocation: String? = null,

    @SerializedName("itemMaxDuration")
    var itemMaxDuration: Int? = null,

    @SerializedName("itemTipAmount")
    val itemTipAmount: String? = null,

    @SerializedName("itemRentDate")
    val itemRentDate: String? = null,

    @SerializedName("itemReturned")
    val itemReturned: Boolean? = null,

    @SerializedName("itemDelivered")
    val itemDelivered: Boolean? = null,

    @SerializedName("itemNotifyLister")
    val itemNotifyLister: Boolean? = null,

    @SerializedName("itemDuration")
    val itemDuration: Int? = null,

    @SerializedName("itemLister")
    val itemLister: Int? = null,

    @SerializedName("itemRenter")
    val itemRenter: String? = null,

    @SerializedName("itemListing")
    val itemListing: Int? = null,

    @SerializedName("itemAvailable")
    var itemAvailable: Boolean = true,

    @SerializedName("itemDriver")
    val itemDriver: String? = null
    ) : java.io.Serializable {
}