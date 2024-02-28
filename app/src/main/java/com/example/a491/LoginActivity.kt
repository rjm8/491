package com.example.a491

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a491.api.ApiService
import com.example.a491.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)

        val createAccount = findViewById<TextView>(R.id.createAccount)
        createAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            GlobalScope.launch(Dispatchers.Main){
                checkAccount(username, password)
            }


        }
    }

    // Logs in if username/password is valid
    suspend fun checkAccount(username: String, password: String) {
        try {
            // Get all users from database
            val apiService = RetrofitClient.instance.create(ApiService::class.java)
            val accounts: List<Account> = apiService.getUsers()

            // Check if any users match input
            var valid = 0
            accounts.forEach { account->
                if (account.username == username && account.password == password) {
                    valid = 1
                }
            }
            if (valid == 1) { // if match is found, post username to sharedPreferences and go to main screen
                finish()
            } else {
                Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Log.e("RegisterActivity", "Error: ${e.message}", e)
        }


    }

}