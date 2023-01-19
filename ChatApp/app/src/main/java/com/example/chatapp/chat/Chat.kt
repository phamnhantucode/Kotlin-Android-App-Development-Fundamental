package com.example.chatapp.chat

import android.media.Image
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.R
import de.hdodenhof.circleimageview.CircleImageView

class Chat: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val backBtn = findViewById<ImageView>(R.id.backBtn)
        val sendBtn = findViewById<ImageView>(R.id.sendBtn)
        val username = findViewById<TextView>(R.id.username)
        val messageInput = findViewById<EditText>(R.id.messageInput)
        val profilePic = findViewById<CircleImageView>(R.id.profilePic)

        //get data from

        backBtn.setOnClickListener {

        }


    }
}