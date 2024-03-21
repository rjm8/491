package com.example.a491

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemFetcher(passedItems: MutableList<Item>, passedAdapter: ItemRecyclerViewAdapter) {
    val items = passedItems
    val itemAdapter = passedAdapter

    fun getItems(userId: Int) {
        var results = JSONArray()
        GlobalScope.launch(Dispatchers.Main) {
            results = getListedItems("https://rapidrentals-9797a640fd53.herokuapp.com/rapidrentals/", results, userId)
            val itemsRawJSON : String = results.toString()
            Log.d("response", itemsRawJSON)

            // Gson used to get data from @SerializedName tags and fill into model objects
            val gson = Gson()
            val arrayItemType = object : TypeToken<List<Item>>() {}.type
            val models : List<Item> = gson.fromJson(itemsRawJSON, arrayItemType, )

            items.addAll(models)
            itemAdapter.notifyDataSetChanged()
        }
    }

    fun getRentingItems(userId: Int) {
        var results = JSONArray()
        GlobalScope.launch(Dispatchers.Main) {
            results = getCurrentlyRentingItemsByUser(
                "https://rapidrentals-9797a640fd53.herokuapp.com/rapidrentals/",
                results,
                userId)
            val itemsRawJSON: String = results.toString()

            // Gson used to get data from @SerializedName tags and fill into model objects
            val gson = Gson()
            val arrayItemType = object : TypeToken<List<Item>>() {}.type
            val models: List<Item> = gson.fromJson(itemsRawJSON, arrayItemType)

            items.addAll(models.reversed())
            itemAdapter.notifyDataSetChanged()
        }
    }

    fun getListingItems(userId: Int) {
        var results = JSONArray()
        GlobalScope.launch(Dispatchers.Main) {
            results = getListedItemsByUser("https://rapidrentals-9797a640fd53.herokuapp.com/rapidrentals/",
                results,
                userId)
            val itemsRawJSON : String = results.toString()

            // Gson used to get data from @SerializedName tags and fill into model objects
            val gson = Gson()
            val arrayItemType = object : TypeToken<List<Item>>() {}.type
            val models : List<Item> = gson.fromJson(itemsRawJSON, arrayItemType)
            items.addAll(models.reversed())
            itemAdapter.notifyDataSetChanged()
        }
    }

    fun getPreviouslyRentedItems(userId: Int) {
        var results = JSONArray()
        GlobalScope.launch(Dispatchers.Main) {
            results = getPreviouslyRentingItemsByUser(
                "https://rapidrentals-9797a640fd53.herokuapp.com/rapidrentals/",
                results,
                userId)
            val itemsRawJSON: String = results.toString()

            // Gson used to get data from @SerializedName tags and fill into model objects
            val gson = Gson()
            val arrayItemType = object : TypeToken<List<Item>>() {}.type
            val models: List<Item> = gson.fromJson(itemsRawJSON, arrayItemType)

            items.addAll(models.reversed())
            itemAdapter.notifyDataSetChanged()
        }
    }

    suspend fun getListedItems(endpoint: String, results: JSONArray, userId: Int) : JSONArray {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)

            // Make the API call
            val userList = apiService.getOtherListedItemData(userId = userId)

            userList.forEach {userData ->
                val jsonObject = JSONObject()
                jsonObject.put("itemListing", userData.listing_id)
                jsonObject.put("itemTitle", userData.item_name)
                jsonObject.put("itemPrice", userData.rental_price_per_day)
                jsonObject.put("itemDesc", userData.description)
                jsonObject.put("image_url", userData.image_url)
                jsonObject.put("itemRetailPrice", userData.retail_price)
                jsonObject.put("itemLocation", userData.location)
                jsonObject.put("itemMaxDuration", userData.max_duration)
                jsonObject.put("itemLister", userData.lister)

                results.put(jsonObject)
            }
        } catch (e: Exception) {
            Log.e("ItemFetcher", "Error: ${e.message}", e)
        }
        return results
    }
    suspend fun getListedItemsByUser(endpoint: String, results: JSONArray, userId: Int) : JSONArray {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)

            // Make the API call
            val userList = apiService.getListedItemData(userId)

            userList.forEach {userData ->
                Log.d("itemFetcher", "item_name: ${userData.item_name}")
                val jsonObject = JSONObject()
                jsonObject.put("id", userData.listing_id)
                jsonObject.put("itemTitle", userData.item_name)
                jsonObject.put("itemPrice", userData.rental_price_per_day)
                jsonObject.put("itemDesc", userData.description)
                jsonObject.put("image_url", userData.image_url)
                jsonObject.put("itemRetailPrice", userData.retail_price)
                jsonObject.put("itemLocation", userData.location)
                jsonObject.put("itemMaxDuration", userData.max_duration)
                jsonObject.put("lister", userData.lister)
                jsonObject.put("itemAvailable", userData.available)
                results.put(jsonObject)
            }
        } catch (e: Exception) {
            Log.e("ItemFetcher", "Error: ${e.message}", e)
        }
        return results
    }

    suspend fun getCurrentlyRentingItemsByUser(endpoint: String, results: JSONArray, userId: Int) : JSONArray {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)

            // Make the API call
            val userList = apiService.getCurrentRentItemData(userId)

            userList.forEach {userData ->
                val jsonObject = JSONObject()
                jsonObject.put("id", userData.rental_id)
                jsonObject.put("itemListerLocation", userData.lister_location)
                jsonObject.put("itemRenterLocation", userData.renter_location)
                jsonObject.put("itemTipAmount", userData.tip_amount_for_driver)
                jsonObject.put("itemRentDate", userData.rental_date)
                jsonObject.put("itemPrice", userData.total_price)
                jsonObject.put("itemReturned", userData.returned)
                jsonObject.put("itemDelivered", userData.delivered)
                jsonObject.put("itemNotifyLister", userData.notify_lister_delivered_safely)
                jsonObject.put("itemDuration", userData.duration)
                jsonObject.put("itemTitle", userData.item_name)
                jsonObject.put("image_url", userData.image_url)
                jsonObject.put("itemDesc", userData.description)
                jsonObject.put("itemLister", userData.lister)
                jsonObject.put("itemRenter", userData.renter)
                jsonObject.put("itemListing", userData.listing)
                jsonObject.put("itemDriver", userData.driver)


                results.put(jsonObject)
            }
        } catch (e: Exception) {
            Log.e("ItemFetcher", "Error: ${e.message}", e)
        }
        return results
    }

    suspend fun getPreviouslyRentingItemsByUser(endpoint: String, results: JSONArray, userId: Int) : JSONArray {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)

            // Make the API call
            val userList = apiService.getPreviousRentItemData(userId)

            userList.forEach {userData ->
                val jsonObject = JSONObject()
                jsonObject.put("id", userData.rental_id)
                jsonObject.put("itemListerLocation", userData.lister_location)
                jsonObject.put("itemRenterLocation", userData.renter_location)
                jsonObject.put("itemTipAmount", userData.tip_amount_for_driver)
                jsonObject.put("itemRentDate", userData.rental_date)
                jsonObject.put("itemPrice", userData.total_price)
                jsonObject.put("itemReturned", userData.returned)
                jsonObject.put("itemDelivered", userData.delivered)
                jsonObject.put("itemNotifyLister", userData.notify_lister_delivered_safely)
                jsonObject.put("itemDuration", userData.duration)
                jsonObject.put("itemTitle", userData.item_name)
                jsonObject.put("image_url", userData.image_url)
                jsonObject.put("itemDesc", userData.description)
                jsonObject.put("itemLister", userData.lister)
                jsonObject.put("itemRenter", userData.renter)
                jsonObject.put("itemListing", userData.listing)
                jsonObject.put("itemDriver", userData.driver)

                results.put(jsonObject)
            }
        } catch (e: Exception) {
            Log.e("ItemFetcher", "Error: ${e.message}", e)
        }
        return results
    }
}