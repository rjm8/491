package com.example.a491

import android.content.Intent
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CurrentlyRentItemDetail :AppCompatActivity() {
    private lateinit var itemImageView : ImageView
    private lateinit var itemTitle : TextView
    private lateinit var itemPrice : TextView
    private lateinit var itemDesc : TextView
    private lateinit var itemRentDate: TextView
    private lateinit var itemDuration: TextView
    private lateinit var itemTipAmount: TextView
    private lateinit var itemStatus: TextView
    private lateinit var returnButton: Button
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.currently_renting_item_detail)

        itemImageView = findViewById(R.id.itemImage)
        itemTitle = findViewById(R.id.itemTitle)
        itemPrice = findViewById(R.id.itemPrice)
        itemDesc = findViewById(R.id.itemDescription)
        itemRentDate = findViewById(R.id.itemRentDate)
        itemDuration = findViewById(R.id.itemDuration)
        itemTipAmount = findViewById(R.id.itemTipAmount)
        itemStatus = findViewById(R.id.itemStatus)

        val item = intent.getSerializableExtra(ITEM_EXTRA) as Item

        itemTitle.text = item.itemTitle

        val price = "$" + item.itemPrice + " per day"
        itemPrice.text = price

        val rentDate = "Rented: " + item.itemRentDate
        itemRentDate.text = rentDate

        val tip = "Tip: $" + item.itemTipAmount
        itemTipAmount.text = tip

        val duration = "Duration: " + item.itemDuration + " days"
        itemDuration.text = duration

        var status = "Status: "
        if (item.itemReturned == true) {
            status += "Returned"
        } else if (item.itemDelivered == true){
            status += "Delivered"
        } else {
            status += "In Transport"
        }
        itemStatus.text = status

        val desc = "Description: " + item.itemDesc
        itemDesc.text = desc

        Glide.with(this)
            .load(item.itemImageUrl)
            .into(itemImageView)

        returnButton = findViewById(R.id.returnButton)
        returnButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                setItemAvailable(item)
                createReturn(item)
                setRentalReturned(item)
            }
        }

        /*
        * Bottom Navigation Bar
        */
        val navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.itemIconTintList = null
        navBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeButton -> {
                    startActivity(Intent(this, MainActivity::class.java))
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


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun createReturn(item: Item) {   //if you get bad request error, it might be because a return already exists with that rental id
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = today.format(formatter)
        val newReturn = Return(
            rental_id = item.rental_id,
            location_of_lister = item.itemListerLocation,
            location_of_renter = item.itemRenterLocation,
            tip_amount = item.itemTipAmount,
            return_date = date
        )

        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        try {
            apiService.createReturn(newReturn)

            Log.d("API", "Return created successfully")
        } catch (e: Exception) {
            Log.e("API", "Return could not be created")
            Log.e("API", "Error: ${e.message}", e)
        }
    }
    suspend fun setItemAvailable(item: Item) {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        try {
            apiService.makeListingAvailable(item.itemListing.toString())

            Log.d("API", "Listing updated successfully")
        } catch (e: Exception) {
            Log.e("API", "Listing could not be updated")
            Log.e("API", "Error: ${e.message}", e)
        }
    }

    suspend fun setRentalReturned(item: Item) {
        Log.d("huh", item.rental_id.toString())
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        try {
            apiService.makeRentalReturned(item.rental_id.toString())

            Log.d("API", "Rental updated successfully")
        } catch (e: Exception) {
            Log.e("API", "Rental could not be updated")
            Log.e("API", "Error: ${e.message}", e)
        }
    }
}