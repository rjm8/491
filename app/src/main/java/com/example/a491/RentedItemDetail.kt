package com.example.a491

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView

class RentedItemDetail :AppCompatActivity() {
    private lateinit var itemImageView : ImageView
    private lateinit var itemTitle : TextView
    private lateinit var itemPrice : TextView
    private lateinit var itemDesc : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rented_item_detail)

        itemImageView = findViewById(R.id.itemImage)
        itemTitle = findViewById(R.id.itemTitle)
        itemPrice = findViewById(R.id.itemPrice)
        itemDesc = findViewById(R.id.itemDescription)

        val item = intent.getSerializableExtra(ITEM_EXTRA) as Item

        itemTitle.text = item.itemTitle
        itemPrice.text = item.itemPrice
        itemDesc.text = item.itemDesc

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