package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Dictionary

class Register : AppCompatActivity() {
    var databaseReference = FirebaseDatabase.getInstance("https://chatapp-44409-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val confirmPasswordInput = findViewById<EditText>(R.id.confirmPasswordInput)

        val registerBtn = findViewById<AppCompatButton>(R.id.registerBtn)

        //check if user already login to
        if (!MemoryData.getData(this).isEmpty()) {
            val intent = Intent(this@Register, MainActivity::class.java)
            intent.putExtra("key", MemoryData.getData(this))
            startActivity(intent)
        }

        registerBtn.setOnClickListener {
            when {
                usernameInput.text.toString().isEmpty() || passwordInput.text.toString().isEmpty() || confirmPasswordInput.text.toString().isEmpty()
                -> {
                    Toast.makeText(this@Register, "Enter complete information", Toast.LENGTH_LONG).show()
                }

                !passwordInput.text.toString().equals(confirmPasswordInput.text.toString())
                -> {
                    Toast.makeText(this@Register, "Passwords do not match", Toast.LENGTH_LONG).show()
                }

                else
                -> {
                    databaseReference.child("user").addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.children.find { it.child("username").value.toString().equals(usernameInput.text.toString())} != null ) {
                                Toast.makeText(this@Register, "Account exits", Toast.LENGTH_LONG).show()
                            } else {
                                val push = databaseReference.child("user").push()
                                push.child("username").setValue(usernameInput.text.toString())
                                push.child("password").setValue(passwordInput.text.toString())
                                push.child("profile_pic").setValue("https://picsum.photos/200")
                                Toast.makeText(this@Register, "Success", Toast.LENGTH_LONG).show()

                                //save data login
                                MemoryData.saveData(push.key!!, this@Register)


                                val intent = Intent(this@Register, MainActivity::class.java)
                                intent.putExtra("key", push.key)
                                startActivity(intent)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
                }
            }

        }
    }
}