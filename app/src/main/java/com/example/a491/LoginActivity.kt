package com.example.a491

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val createAccount = findViewById<TextView>(R.id.createAccount)
        createAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

}