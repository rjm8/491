package com.example.a491

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.a491.api.RetrofitClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ListedItemDetail :AppCompatActivity() {
    private lateinit var itemImageView : ImageView
    private lateinit var itemTitle : TextView
    private lateinit var itemPrice : TextView
    private lateinit var itemLocation: TextView
    private lateinit var itemRetailPrice: TextView
    private lateinit var itemMaxDuration: TextView
    private lateinit var itemDesc : TextView
    private lateinit var itemEdit : Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listing_item_detail)

        itemImageView = findViewById(R.id.itemImage)
        itemTitle = findViewById(R.id.itemTitle)
        itemPrice = findViewById(R.id.itemPrice)
        itemLocation = findViewById(R.id.itemLocation)
        itemRetailPrice = findViewById(R.id.itemRetailPrice)
        itemMaxDuration = findViewById(R.id.itemMaxDuration)
        itemDesc = findViewById(R.id.itemDescription)
        itemEdit = findViewById(R.id.editButton)

        val item = intent.getSerializableExtra(ITEM_EXTRA) as Item

        itemTitle.text = item.itemTitle
        val price = "$" + item.itemPrice + " per day"
        itemPrice.text = price
        val location = "Location: " + item.itemLocation
        itemLocation.text = location
        val retailPrice = "Retail: $" + item.itemRetailPrice
        itemRetailPrice.text = retailPrice
        val maxDuration = "Max Days: " + item.itemMaxDuration
        itemMaxDuration.text = maxDuration
        val desc = "Description: " + item.itemDesc
        itemDesc.text = desc

        Glide.with(this)
            .load(item.itemImageUrl)
//            .load(ContextCompat.getDrawable(this, R.drawable.shekhmus))
            .into(itemImageView)

        /*
        * Posting Rental to Database
        */
        itemEdit.setOnClickListener {
            Toast.makeText(this, "Edit Listing", Toast.LENGTH_SHORT).show()
        }


        /*
        * Bottom Navigation Bar
        */
        val navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.itemIconTintList = null
        navBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeButton -> {
                    finish() // this acts as a back button
                }
                R.id.postButton -> {
                    startActivity(Intent(this, RentFormActivity::class.java))
                }
                R.id.profileButton -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                }
            }
            navBar.itemIconTintList = null
            true
        }
    }
}