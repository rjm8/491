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

    fun getItems() {
        var results = JSONArray()
        GlobalScope.launch(Dispatchers.Main) {
            results = getListedItems("https://rapidrentals-9797a640fd53.herokuapp.com/rapidrentals/", results)
            val itemsRawJSON : String = results.toString()
            Log.d("response", itemsRawJSON)

            // Gson used to get data from @SerializedName tags and fill into model objects
            val gson = Gson()
            val arrayItemType = object : TypeToken<List<Item>>() {}.type
            val models : List<Item> = gson.fromJson(itemsRawJSON, arrayItemType)

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

            items.addAll(models)
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
            items.addAll(models)
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

            items.addAll(models)
            itemAdapter.notifyDataSetChanged()
        }
    }

    suspend fun getListedItems(endpoint: String, results: JSONArray) : JSONArray {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ApiService::class.java)

            // Make the API call
            val userList = apiService.getAllListedItemData()

            userList.forEach {userData ->
                val jsonObject = JSONObject()
                jsonObject.put("id", userData.id)
                jsonObject.put("rental_price_per_day", userData.rental_price_per_day)
                jsonObject.put("itemPrice", userData.retail_price)
                jsonObject.put("itemTitle", userData.item_name)
                jsonObject.put("itemDesc", userData.description)
                jsonObject.put("max_duration", userData.max_duration)
                jsonObject.put("lister", userData.lister)
                jsonObject.put("image_url", userData.image_url)
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
                jsonObject.put("id", userData.id)
                jsonObject.put("rental_price_per_day", userData.rental_price_per_day)
                jsonObject.put("itemPrice", userData.retail_price)
                jsonObject.put("itemTitle", userData.item_name)
                jsonObject.put("itemDesc", userData.description)
                jsonObject.put("max_duration", userData.max_duration)
                jsonObject.put("lister", userData.lister)

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
                jsonObject.put("id", userData.id)
                jsonObject.put("rental_date", userData.rental_date)
                jsonObject.put("itemPrice", userData.total_price)
                jsonObject.put("duration", userData.duration)
                jsonObject.put("itemTitle", userData.item_name)
                jsonObject.put("lister", userData.lister)
                jsonObject.put("renter", userData.renter)
                jsonObject.put("listing", userData.listing)
                jsonObject.put("location_of_renter", userData.location_of_renter)
                jsonObject.put("location_of_lister", userData.location_of_lister)

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
                jsonObject.put("id", userData.id)
                jsonObject.put("rental_date", userData.rental_date)
                jsonObject.put("itemPrice", userData.total_price)
                jsonObject.put("duration", userData.duration)
                jsonObject.put("itemTitle", userData.item_name)
                jsonObject.put("lister", userData.lister)
                jsonObject.put("renter", userData.renter)
                jsonObject.put("listing", userData.listing)
                jsonObject.put("location_of_renter", userData.location_of_renter)
                jsonObject.put("location_of_lister", userData.location_of_lister)

                results.put(jsonObject)
            }
        } catch (e: Exception) {
            Log.e("ItemFetcher", "Error: ${e.message}", e)
        }
        return results
    }
}