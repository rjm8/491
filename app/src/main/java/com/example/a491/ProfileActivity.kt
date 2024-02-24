package com.example.a491

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfileActivity : AppCompatActivity() {
    val renting_items = mutableListOf<Item>()
    val listing_items = mutableListOf<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        //val sharedPref = activity?.get

        val renting_recycler = findViewById<RecyclerView>(R.id.rentingRecycler)
        val listing_recycler = findViewById<RecyclerView>(R.id.listingRecycler)

        renting_recycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        listing_recycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)


        val renting_adapter = ItemRecyclerViewAdapter(renting_items, this, false, true)
        val listing_adapter = ItemRecyclerViewAdapter(listing_items, this, false, false)

        renting_recycler.adapter = renting_adapter
        listing_recycler.adapter = listing_adapter

        val renting_fetcher = ItemFetcher(renting_items, renting_adapter)
        val listing_fetcher = ItemFetcher(listing_items, listing_adapter)
        renting_fetcher.getRentingItems()
        listing_fetcher.getListingItems()


    }
}