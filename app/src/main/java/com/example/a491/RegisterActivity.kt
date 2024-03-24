package com.example.a491

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.a491.api.RetrofitClient
import com.example.a491.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private lateinit var sharedpreferences: SharedPreferences
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val fNameInput = findViewById<EditText>(R.id.firstNameInput)
        val lNameInput = findViewById<EditText>(R.id.lastNameInput)
        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        //val emailInput = findViewById<EditText>(R.id.emailInput)
        val locationInput = findViewById<EditText>(R.id.locationInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val phoneInput = findViewById<EditText>(R.id.phoneInput)
        val paymentInput = findViewById<EditText>(R.id.paymentInput)
        val button = findViewById<Button>(R.id.registerButton)

        button.setOnClickListener {
            val newAccount = Account(
                fNameInput.text.toString(),
                lNameInput.text.toString(),
                usernameInput.text.toString(),
                passwordInput.text.toString(),
                phoneInput.text.toString(),
                locationInput.text.toString(),
                paymentInput.text.toString()
            )
            GlobalScope.launch(Dispatchers.Main) {
                postUserData(newAccount)
            }

        }

    }

    suspend fun postUserData (user: Account) {
        try {
            val apiService = RetrofitClient.instance.create(ApiService::class.java)
            apiService.postUser(user)

            // authenticate user to receive user id
            val returnMessage: ReturnMessage = apiService.checkPassword(user)

            // store username in sharedpreferences and login
            sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            val editor = sharedpreferences.edit()
            editor.clear()
            editor.putString(getString(R.string.username_key), user.username)
            editor.putInt(getString(R.string.user_id_key), returnMessage.user_id)
            editor.putString(getString(R.string.user_location_string), user.location)
            editor.apply()
            startActivity(Intent(this, MainActivity::class.java))
        } catch (e: Exception) {
            Log.e("RegisterActivity", "Error: ${e.message}", e)
        }

    }
}