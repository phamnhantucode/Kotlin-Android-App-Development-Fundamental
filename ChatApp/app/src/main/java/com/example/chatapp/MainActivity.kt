package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.messages.MessagesAdapter
import com.example.chatapp.messages.MessagesList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {
    var messagesLists: MutableList<MessagesList> = ArrayList()
    var username = ""
    var key = ""
    var recyclerView: RecyclerView? = null
    val databaseReference = FirebaseDatabase.getInstance("https://chatapp-44409-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //get view from XML
        recyclerView = findViewById(R.id.listChat)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        val userProfilePic = findViewById<CircleImageView>(R.id.avatarImg)

        //get intent data
        key = intent.getStringExtra("key").toString()
        databaseReference.child("user").child(key).get().addOnSuccessListener {
            if (it.value == null) {
                logOut()
            }
        }
        //get profile pic from database
        databaseReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val profilePicUrl = snapshot.child("user").child(key).child("profile_pic").value.toString()
                Picasso.get().load(profilePicUrl).into(userProfilePic)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messagesLists.clear()
                for (data in snapshot.child("user").children) {
                    if (!data.key.toString().equals(key)) {
                        messagesLists.add(MessagesList(data.child("username").value.toString(), "", data.child("profile_pic").value.toString(), 0))
                    }
                }
                recyclerView!!.adapter = MessagesAdapter(this@MainActivity, messagesLists)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        userProfilePic.setOnClickListener {
            logOut()
        }
    }

    fun logOut() {
        MemoryData.saveData("", this)
        startActivity(Intent(this@MainActivity, Register::class.java))
    }
}