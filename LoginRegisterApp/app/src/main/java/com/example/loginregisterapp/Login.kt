package com.example.loginregisterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val accountInput = findViewById<EditText>(R.id.accountInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginBtn = findViewById<AppCompatButton>(R.id.loginBtn)
        val directRegisterBtn = findViewById<TextView>(R.id.directRegisterBtn)

        loginBtn.setOnClickListener {
            if (accountInput.text.isEmpty() || passwordInput.text.isEmpty()) {
                Toast.makeText(this@Login, "Please enter username or password to login", Toast.LENGTH_LONG).show()
            } else {

            }
        }

        directRegisterBtn.setOnClickListener {
            startActivity(Intent(this@Login, Register::class.java))
        }
    }
}