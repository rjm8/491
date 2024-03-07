package com.example.a491

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.a491.api.RetrofitClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ItemDetail :AppCompatActivity() {
    private lateinit var itemImageView : ImageView
    private lateinit var itemTitle : TextView
    private lateinit var itemPrice : TextView
    private lateinit var itemLocation: TextView
    private lateinit var itemRetailPrice: TextView
    private lateinit var itemMaxDuration: TextView
    private lateinit var itemDesc : TextView
    private lateinit var itemBuy : Button
    private lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_detail)

        itemImageView = findViewById(R.id.itemImage)
        itemTitle = findViewById(R.id.itemTitle)
        itemPrice = findViewById(R.id.itemPrice)
        itemLocation = findViewById(R.id.itemLocation)
        itemRetailPrice = findViewById(R.id.itemRetailPrice)
        itemMaxDuration = findViewById(R.id.itemMaxDuration)
        itemDesc = findViewById(R.id.itemDescription)
        itemBuy = findViewById(R.id.buyButton)

        val item = intent.getSerializableExtra(ITEM_EXTRA) as Item
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

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
        itemBuy.setOnClickListener {
            val today = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = today.format(formatter)
            val price = "500.00"
            val duration = 10 // this should change and be displayed on the page
            val renter = 17 // this should be the userID of the currently logged in user
            val listing = 1 // this should be the listingID which should be associated with this page
            val lister = 16 // this should be the userID of the lister which should be displayed on this page
            val tip = "5.00"
            val itemName = itemTitle.text.toString()
            val listerLocation = "Shekhmus's home" // this should be associated w lister userID
            val renterLocation = "Shekhmus's friend's house" // this should be associated w renter userID

            Log.d("TEST", item.itemListing.toString())



            val rental = Rental(
                rental_date = date,
                total_price = item.itemPrice,
                duration = duration,
                lister = item.itemLister,
                renter = sharedPreferences.getInt(getString(R.string.user_id_key), -1),
                listing = item.itemListing,
                tip_amount_for_driver = tip,
                item_name = item.itemTitle,
                lister_location = item.itemLocation,
                renter_location = sharedPreferences.getString(getString(R.string.user_location_string), "no loc")
            )

            val apiService = RetrofitClient.instance.create(ApiService::class.java)
            val call = apiService.createRental(rental)

            call.enqueue(object : retrofit2.Callback<Void> {
                override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d("API", "Item rented successfully")
                    } else {
                        Log.e("API", "Failed to rent item: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("API", "Failed to rent item", t)
                }
            })

            // TODO: Make an intent that goes to the item's page after it is created or back to main screen
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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