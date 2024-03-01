package com.example.a491

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.FirebaseApp
//import com.google.firebase.appcheck.FirebaseAppCheck
//import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

/*
to post listings, we need:
 - image
 - item name
 - description
 - lister id (user id)
 - user's location from user table
 - price
 - price per day
 - max duration
 */

private const val TAG = "RentFormActivity"
class RentFormActivity : AppCompatActivity() {
    private lateinit var rentImageUpload: Button
    private lateinit var rentItemName: EditText
    private lateinit var rentItemDescription: EditText
    private lateinit var rentItemPrice: EditText
    private lateinit var rentSubmitForm: Button
    private lateinit var pickImageResultLauncher: ActivityResultLauncher<String>
    private lateinit var layout: ConstraintLayout
    private var constraintSet = ConstraintSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_form)

        rentImageUpload = findViewById(R.id.formUploadImage)
        rentItemName = findViewById(R.id.formProductNameText)
        rentItemDescription = findViewById(R.id.formDescriptionText)
        rentItemPrice = findViewById(R.id.formPriceText)
        rentSubmitForm = findViewById(R.id.rentSubmitButton)
        layout = findViewById(R.id.rentalConstraintLayout)
        constraintSet.clone(layout)

//        val itemHint = SpannableString("Product Name\nEnter your product Name")
//
//        // Set different colors for each line
//        itemHint.setSpan(ForegroundColorSpan(Color.WHITE), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        itemHint.setSpan(ForegroundColorSpan(Color.DKGRAY), 13, itemHint.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//        rentItemName.hint = itemHint


        rentImageUpload.setOnClickListener {
            pickImageResultLauncher.launch("image/*")
            Toast.makeText(this, "Image Uploader Test", Toast.LENGTH_SHORT).show()
        }
        pickImageResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val imageView: ImageButton = findViewById(R.id.listingImage)
                imageView.setImageURI(uri)
                sendToFirebase(uri)
                Log.i("shekhmus", uri.toString())
                constraintSet.connect(
                    R.id.productNameTitle,
                    ConstraintSet.TOP,
                    R.id.listingImage,
                    ConstraintSet.BOTTOM
                )
                constraintSet.applyTo(layout)
                imageView.visibility = ImageButton.VISIBLE
                rentImageUpload.visibility = Button.INVISIBLE
            }
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
        /*
        * Bottom Navigation Bar
        */
        val navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.itemIconTintList = null
        navBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeButton -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

                R.id.postButton -> {
//                    Toast.makeText(this, "Post Pressed", Toast.LENGTH_SHORT).show()
                }

                R.id.profileButton -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                }
            }
            navBar.itemIconTintList = null

            true
        }
    }

    private fun sendToFirebase(uri: Uri) {
        val storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("images/shekhmus.jpg")

        imageRef.putFile(uri)
            .addOnSuccessListener {
                it.metadata?.reference?.downloadUrl?.addOnSuccessListener { downloadUri ->
                    val downloadURL = downloadUri.toString()
                    Log.d("shekhmus", downloadURL)
                    val db = Firebase.firestore
                    val imageInfo = hashMapOf(
                        "imageUrl" to downloadURL,
                        "timestamp" to FieldValue.serverTimestamp()
                    )

                    db.collection("images").add(imageInfo)
                        .addOnSuccessListener {
                            Log.d("UploadSuccess", "Image URL stored in Firestore: $downloadURL")
                        }
                        .addOnFailureListener {
                            Log.e("error", it.toString())
                        }
                }
            }
            .addOnFailureListener {
                Log.e("error", it.toString())
            }
    }
}