package com.example.a491

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView

class PreviouslyRentItemDetail :AppCompatActivity() {
    private lateinit var itemImageView : ImageView
    private lateinit var itemTitle : TextView
    private lateinit var itemPrice : TextView
    private lateinit var itemDesc : TextView
    private lateinit var itemRentDate: TextView
    private lateinit var itemDuration: TextView
    private lateinit var itemTipAmount: TextView
    private lateinit var itemStatus: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.previously_rented_item_detail)

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
                    finish()
                }
            }
            navBar.itemIconTintList = null
            true
        }
    }
}