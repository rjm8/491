package com.example.a491

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a491.api.ApiService
import com.example.a491.api.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {
    private lateinit var sharedpreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)

        val createAccount = findViewById<TextView>(R.id.createAccount)
        createAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
/*
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        var users = listOf<Account>()
        GlobalScope.launch(Dispatchers.Main){
            users = getAllUsers(apiService)
        }

 */

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
            // send input data to checkPassword API
            val apiService = RetrofitClient.instance.create(ApiService::class.java)
            val credentials = Account(
                "",
                "",
                username,
                password,
                "",
                "",
                ""
            )

            // If valid, returns message and user id, throws error otherwise
            val returnMessage: ReturnMessage = apiService.checkPassword(credentials)

            if (returnMessage.message == "Password is correct") {
                sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
                val editor = sharedpreferences.edit()
                editor.clear()
                editor.putString(getString(R.string.username_key), username)
                editor.putInt(getString(R.string.user_id_key), returnMessage.user_id)
                editor.putString(getString(R.string.user_location_string), returnMessage.location)
                editor.apply()
                startActivity(Intent(this, ProfileActivity::class.java))
            } else {
                Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Log.e("RegisterActivity", "Error: ${e.message}", e)
            Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show()
        }


    }

}

class Account (
    val first_name: String,
    val last_name: String,
    val username: String,
    val password: String,
    val phone_number: String,
    val location: String,
    val payment_method: String
) {
}

class ReturnMessage(val message: String, val user_id: Int, val location: String){
}