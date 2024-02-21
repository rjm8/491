package com.example.a491

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "RentFormActivity"
class RentFormActivity : AppCompatActivity() {
    private lateinit var rentImageUpload: Button
    private lateinit var rentItemName: EditText
    private lateinit var rentItemDescription: EditText
    private lateinit var rentItemPrice: EditText
    private lateinit var rentSubmitForm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_form)

        rentImageUpload = findViewById(R.id.formUploadImage)
        rentItemName = findViewById(R.id.formProductNameText)
        rentItemDescription = findViewById(R.id.formDescriptionText)
        rentItemPrice = findViewById(R.id.formPriceText)
        rentSubmitForm = findViewById(R.id.rentSubmitButton)

//        val itemHint = SpannableString("Product Name\nEnter your product Name")
//
//        // Set different colors for each line
//        itemHint.setSpan(ForegroundColorSpan(Color.WHITE), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        itemHint.setSpan(ForegroundColorSpan(Color.DKGRAY), 13, itemHint.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//        rentItemName.hint = itemHint


        rentImageUpload.setOnClickListener {
            Toast.makeText(this, "Image Uploader Test", Toast.LENGTH_SHORT).show()
        }
        rentSubmitForm.setOnClickListener {
            val imagePhotoPath = "TO DO"
            val itemName = rentItemName.getText().toString()
            val itemDescription = rentItemDescription.getText().toString()
            val itemPrice = rentItemPrice.getText().toString()

            rentItemName.getText().clear()
            rentItemDescription.getText().clear()
            rentItemPrice.getText().clear()

            // TODO: Processing of items

            // TODO: Make an intent that goes to the item's page after it is created or back to main screen
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}