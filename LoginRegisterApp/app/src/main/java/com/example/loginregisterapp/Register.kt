package com.example.loginregisterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    //Create object databaseReference to access firebase's realtime database
    var databaseReference = FirebaseDatabase.getInstance("https://loginregister-d163d-default-rtdb.asia-southeast1.firebasedatabase.app").getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val confirmPasswordInput = findViewById<EditText>(R.id.confirmPasswordInput)

        val registerBtn = findViewById<AppCompatButton>(R.id.registerBtn)
        val directLoginBtn = findViewById<TextView>(R.id.directLoginBtn)

        registerBtn.setOnClickListener {
            //check input
            when {
                usernameInput.text.isEmpty() || passwordInput.text.isEmpty() || confirmPasswordInput.text.isEmpty()
                    -> Toast.makeText(this, "Please fill all", Toast.LENGTH_LONG).show()

                !passwordInput.text.toString().equals(confirmPasswordInput.text.toString())
                    -> Toast.makeText(this@Register, "Password not matching", Toast.LENGTH_LONG).show()

                else -> {

                    databaseReference.child("user").addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            //check if account exits
                            if (snapshot.children.find { it.child("username").value.toString().equals(usernameInput.text.toString())} != null ){
                                Toast.makeText(this@Register, "Account exits", Toast.LENGTH_LONG).show()
                            }
                            else {

                                val account = databaseReference.child("user").push()
                                account.child("username").setValue(usernameInput.text.toString())
                                account.child("password").setValue(passwordInput.text.toString())
                                Toast.makeText(this@Register, "Success", Toast.LENGTH_LONG).show()
                                finish()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })

                }
            }



        }


        directLoginBtn.setOnClickListener {
            finish()
        }
    }
}