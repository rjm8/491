package com.example.a491

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject

class ItemFetcher (passedItems: MutableList<Item>, passedAdapter: ItemRecyclerViewAdapter){
    val items = passedItems
    val itemAdapter = passedAdapter

    // updates movies list that was passed by reference and notifies adapter
    fun getItems() {
        val results = JSONArray()

        for (i in 0..9) {
            val jsonObject = JSONObject()
            val desc = "really\nlong\ndescription\nto\ntest\nout\nthe\nscroll\nview\nfunctionality\nwhich\nis\nan\nimportant\ncase\nto\nconsider\n$i"
            jsonObject.put("itemTitle", "Really Long Item Title $i")
            jsonObject.put("itemPrice", "$1000000.0$i")
            jsonObject.put("itemDesc", desc)
            jsonObject.put("itemImage", "itemImage$i")
            results.put(jsonObject)
        }
        val itemsRawJSON : String = results.toString()

        // Gson used to get data from @SerializedName tags and fill into model objects
        val gson = Gson()
        val arrayItemType = object : TypeToken<List<Item>>() {}.type
        val models : List<Item> = gson.fromJson(itemsRawJSON, arrayItemType)

        items.addAll(models)
        itemAdapter.notifyDataSetChanged()
    }

    fun getRentingItems() {
        val results = JSONArray()

        for (i in 1..10) {
            val jsonObject = JSONObject()
            jsonObject.put("itemTitle", "itemTitle$i")
            jsonObject.put("itemPrice", "itemPrice$i")
            jsonObject.put("itemDesc", "itemDesc$i")
            jsonObject.put("itemImage", "itemImage$i")

            results.put(jsonObject)
        }
        val itemsRawJSON : String = results.toString()

        // Gson used to get data from @SerializedName tags and fill into model objects
        val gson = Gson()
        val arrayItemType = object : TypeToken<List<Item>>() {}.type
        val models : List<Item> = gson.fromJson(itemsRawJSON, arrayItemType)

        items.addAll(models)
        itemAdapter.notifyDataSetChanged()
    }

    fun getListingItems() {
        val results = JSONArray()

        for (i in 1..10) {
            val jsonObject = JSONObject()
            jsonObject.put("itemTitle", "itemTitle$i")
            jsonObject.put("itemPrice", "itemPrice$i")
            jsonObject.put("itemDesc", "itemDesc$i")
            jsonObject.put("itemImage", "itemImage$i")

            results.put(jsonObject)
        }
        val itemsRawJSON : String = results.toString()

        // Gson used to get data from @SerializedName tags and fill into model objects
        val gson = Gson()
        val arrayItemType = object : TypeToken<List<Item>>() {}.type
        val models : List<Item> = gson.fromJson(itemsRawJSON, arrayItemType)

        items.addAll(models)
        itemAdapter.notifyDataSetChanged()
    }
}