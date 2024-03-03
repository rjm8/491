package com.example.a491

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.firestore.ktx.firestore
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.a491.api.RetrofitClient
import retrofit2.Call

private const val TAG = "RentFormActivity"
class RentFormActivity : AppCompatActivity() {
    private lateinit var rentImageUpload: Button
    private lateinit var rentItemName: EditText
    private lateinit var rentItemDescription: EditText
    private lateinit var rentItemPrice: EditText
    private lateinit var rentItemPPD: EditText
    private lateinit var rentItemDuration: EditText
    private lateinit var rentSubmitForm: Button
    private lateinit var pickImageResultLauncher: ActivityResultLauncher<String>
    private lateinit var layout: ConstraintLayout
    private lateinit var imageButton: ImageButton
    private var localUriString: String = ""
    private lateinit var localUri: Uri
    private var constraintSet = ConstraintSet()
    private var imageUrl: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_form)

        rentImageUpload = findViewById(R.id.formUploadImage)
        rentItemName = findViewById(R.id.formProductNameText)
        rentItemDescription = findViewById(R.id.formDescriptionText)
        rentItemPrice = findViewById(R.id.formPriceText)
        rentSubmitForm = findViewById(R.id.rentSubmitButton)
        rentItemPPD = findViewById(R.id.formPPDText)
        rentItemDuration = findViewById(R.id.formDurationText)
        layout = findViewById(R.id.rentalConstraintLayout)
        imageButton = findViewById(R.id.listingImage)
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
        }

        imageButton.setOnClickListener {
            pickImageResultLauncher.launch("image/*")
        }

        pickImageResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageButton.setImageURI(uri)
                localUriString = uri.toString()
                localUri = uri
                constraintSet.connect(
                    R.id.productNameTitle,
                    ConstraintSet.TOP,
                    R.id.listingImage,
                    ConstraintSet.BOTTOM
                )
                constraintSet.applyTo(layout)
                imageButton.visibility = ImageButton.VISIBLE
                rentImageUpload.visibility = Button.INVISIBLE
            }
        }

        rentSubmitForm.setOnClickListener {
            val userId = 27  // this should be the actual user id
            // image url stored in imageUrl
            val itemName = rentItemName.getText().toString()
            val itemDescription = rentItemDescription.getText().toString()
            val retailPrice = rentItemPrice.getText().toString()
            val location = "shekhmus's home" // this should be retrieved from user's row in users table
            val rentalPrice = rentItemPPD.getText().toString()
            val duration = rentItemDuration.getText().toString()

            if (localUriString.isNotEmpty() && itemName.isNotEmpty() && itemDescription.isNotEmpty()
                && retailPrice.isNotEmpty() && rentalPrice.isNotEmpty() && duration.isNotEmpty() ) {
                // TODO:  SEND ITEMS TO DATABASE
                sendToFirebase(localUri) { success ->
                    if (success) {
                        val listing = Listing(
                            rental_price_per_day = rentalPrice,
                            retail_price = retailPrice,
                            item_name = itemName,
                            image_url = imageUrl,
                            description = itemDescription,
                            max_duration = duration.toInt(),
                            lister = userId
                        )
                        val apiService = RetrofitClient.instance.create(ApiService::class.java)
                        val call = apiService.createListing(listing)

                        call.enqueue(object : retrofit2.Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                                if (response.isSuccessful) {
                                    Log.d(TAG, "Listing created successfully")
                                } else {
                                    Log.e(TAG, "Failed to create listing: ${response.errorBody()?.string()}")
                                }
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Log.e(TAG, "Failed to create listing", t)
                            }
                        })
                    } else {
                        Toast.makeText(this, "Failed to upload image or update Firestore", Toast.LENGTH_SHORT).show()
                    }
                }

                // TODO: Make an intent that goes to the item's page after it is created or back to main screen
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Incomplete Form", Toast.LENGTH_SHORT).show()
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

    private fun sendToFirebase(uri: Uri, onCompletion: (Boolean) -> Unit){
        val storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("images/${System.currentTimeMillis()}.jpg")

        imageRef.putFile(uri)
            .addOnSuccessListener {
                it.metadata?.reference?.downloadUrl?.addOnSuccessListener { downloadUri ->
                    val downloadURL = downloadUri.toString()
//                    Log.d("shekhmus", downloadURL)
                    val db = Firebase.firestore
                    imageUrl = downloadURL
                    val imageInfo = hashMapOf(
                        "imageUrl" to downloadURL,
                        "timestamp" to FieldValue.serverTimestamp()
                    )
                    onCompletion(true)
                    db.collection("images").add(imageInfo)
                        .addOnSuccessListener {
//                            Log.d("UploadSuccess", "Image URL stored in Firestore: $downloadURL")
                            onCompletion(true)
                        }
                        .addOnFailureListener {
                            Log.e("error", it.toString())
                            onCompletion(false)
                        }
                }
            }
            .addOnFailureListener {
                Log.e("error", it.toString())
                onCompletion(false)
            }
    }
}