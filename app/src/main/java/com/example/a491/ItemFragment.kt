package com.example.a491

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class ItemFragment: Fragment(), OnListFragmentInteractionListener {

    /* View Construction */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.itemRecyclerList) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(
            context,
            2
        ) // Change span count to show how many columns of items to show
        updateAdapter(progressBar, recyclerView)
        return view
    }

    /* Populate Recycler View with items */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()
        progressBar.hide()
        val results = JSONArray()

        for (i in 1..8) {
            val jsonObject = JSONObject()
            jsonObject.put("itemTitle", "itemTitle$i")
            jsonObject.put("itemPrice", "itemPrice$i")
            jsonObject.put("itemDesc", "itemDesc$i")
            jsonObject.put("itemImage", "itemImage$i")

            results.put(jsonObject)
        }
        val itemsRawJSON : String = results.toString()
        Log.v("Rapid Delivery Rentals - Item Fragment", itemsRawJSON)

        Log.v("Rapid Delivery Rentals - Item Fragment", itemsRawJSON)

        // Gson used to get data from @SerializedName tags and fill into model objects
        val gson = Gson()
        val arrayMovieType = object : TypeToken<List<Item>>() {}.type
        val models : List<Item> = gson.fromJson(itemsRawJSON, arrayMovieType)

        // Update Item Recycler View
        recyclerView.adapter = ItemRecyclerViewAdapter(models)

        Log.d("Rapid Delivery Rentals - Item Fragment", "response successful")

        // TODO: Create Database connection

        // TODO: populate items into a JSONObject or JSON Array
        // On Successful Connection
//        override fun onSuccess(
//            statusCode: Int,
//            headers: Headers,
//            json: JsonHttpResponseHandler.JSON
//        ) {
//            progressBar.hide()
//
//            // Get results and store in an array
//            val resultsArray: JSONArray = json.jsonObject.getJSONArray("results")
//            // Process Array to get individual items
//            val itemsRawJSON : String = resultsArray.toString()
//            Log.v("Rapid Delivery Rentals - Item Fragment", itemsRawJSON)
//
//            // Gson used to get data from @SerializedName tags and fill into model objects
//            val gson = Gson()
//            val arrayMovieType = object : TypeToken<List<Item>>() {}.type
//            val models : List<Item> = gson.fromJson(itemsRawJSON, arrayMovieType)
//
//            // Update Item Recycler View
//            recyclerView.adapter = ItemRecyclerViewAdapter(models, this@ItemFragment)
//
//            Log.d("Rapid Delivery Rentals - Item Fragment", "response successful")
//        }
//        // On Failed Connection
//            override fun onFailure(
//                statusCode: Int,
//                headers: Headers?,
//                errorResponse: String,
//                t: Throwable?
//            ) {
//            // The wait for a response is over
//            progressBar.hide()
//
//            // If the error is not null, log it!
//            t?.message?.let {
//                Log.e("BestSellerBooksFragment", errorResponse)
//        }
//    }]

    }
    override fun onItemClick(item: Item) {
//        Toast.makeText(context, "Item: " + item.itemName, Toast.LENGTH_LONG).show()
    }
}