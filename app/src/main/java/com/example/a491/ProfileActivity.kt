package com.example.a491

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
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
    val prev_rented_items = mutableListOf<Item>()
    lateinit var sharedpreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // get username from shared preferences
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val username = sharedpreferences.getString(getString(R.string.username_key), "defUsername")


        val usernameView = findViewById<TextView>(R.id.username)
        usernameView.text = username

        val renting_recycler = findViewById<RecyclerView>(R.id.rentingRecycler)
        val listing_recycler = findViewById<RecyclerView>(R.id.listingRecycler)
        val prev_rented_recycler = findViewById<RecyclerView>(R.id.prevRentedRecycler)

        renting_recycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        listing_recycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        prev_rented_recycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)


        val renting_adapter = ItemRecyclerViewAdapter(renting_items, this, false, true)
        val listing_adapter = ItemRecyclerViewAdapter(listing_items, this, false, false)
        val prev_rented_adapter = ItemRecyclerViewAdapter(prev_rented_items, this, false, false)

        renting_recycler.adapter = renting_adapter
        listing_recycler.adapter = listing_adapter
        prev_rented_recycler.adapter = prev_rented_adapter


        val renting_fetcher = ItemFetcher(renting_items, renting_adapter)
        val listing_fetcher = ItemFetcher(listing_items, listing_adapter)
        val prev_rented_fetcher = ItemFetcher(prev_rented_items, listing_adapter)

        renting_fetcher.getRentingItems()
        listing_fetcher.getListingItems()
        prev_rented_fetcher.getListingItems()

        // logout button
        val logoutButton = findViewById<ImageButton>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            // remove username from shared preferences, send to login screen
            val editor = sharedpreferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(this, LoginActivity::class.java))
        }



        val navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.itemIconTintList = null
        navBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeButton -> {
                    //Toast.makeText(this, "Home Pressed", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                R.id.postButton -> {
                    startActivity(Intent(this, RentFormActivity::class.java))
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