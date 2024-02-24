package com.example.a491

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {
    val renting_items = mutableListOf<Item>()
    val listing_items = mutableListOf<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // When we implement accounts, we'll use shared preferences to store
        // the currently logged in account name, for now we'll hard code
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(getString(R.string.username_key), "TestName")
            apply()
        }
        val username = sharedPref.getString(getString(R.string.username_key), "Username")

        val usernameView = findViewById<TextView>(R.id.username)
        usernameView.text = username

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


        val navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.itemIconTintList = null
        navBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeButton -> {
                    //Toast.makeText(this, "Home Pressed", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                R.id.postButton -> {
                    Toast.makeText(this, "Post Pressed", Toast.LENGTH_SHORT).show()
                }
                R.id.profileButton -> {
                    // Do nothing
                }
            }
            navBar.itemIconTintList = null

            true
        }



    }
}