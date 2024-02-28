package com.example.a491

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
        val button = findViewById<Button>(R.id.registerButton)

        button.setOnClickListener {
            val newAccount = Account(
                fNameInput.text.toString(),
                lNameInput.text.toString(),
                usernameInput.text.toString(),
                passwordInput.text.toString(),
                locationInput.text.toString()
            )
            val job = GlobalScope.launch(Dispatchers.Main) {
                postUserData(newAccount)
            }

        }

    }

    suspend fun postUserData (user: Account) {
        try {
            val apiService = RetrofitClient.instance.create(ApiService::class.java)
            apiService.postUser(user)
            finish()
        } catch (e: Exception) {
            Log.e("RegisterActivity", "Error: ${e.message}", e)
        }

    }
}