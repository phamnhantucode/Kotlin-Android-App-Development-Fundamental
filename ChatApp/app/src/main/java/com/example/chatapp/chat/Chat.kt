package com.example.chatapp.chat

import android.media.Image
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class Chat: AppCompatActivity() {

    val databaseReference = FirebaseDatabase.getInstance("https://chatapp-44409-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference()
    var keyChat: String? = null
    var chatRecyclerView: RecyclerView? = null
    var chatLists = ArrayList<ChatList>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val backBtn = findViewById<ImageView>(R.id.backBtn)
        val sendBtn = findViewById<ImageView>(R.id.sendBtn)
        val username = findViewById<TextView>(R.id.username)
        val messageInput = findViewById<EditText>(R.id.messageInput)
        val profilePic = findViewById<CircleImageView>(R.id.profilePic)
        chatRecyclerView = findViewById<RecyclerView>(R.id.chatRecyclerView)
        chatRecyclerView.setHasFixedSize(true)
        chatRecyclerView.layoutManager = LinearLayout(this@Chat)
        //get data from messages adapter class

        keyChat = intent.getStringExtra("key_chat").toString()
        val user = intent.getStringExtra("user")!!
        val partner = intent.getStringExtra("partner")!!
        Picasso.get().load(intent.getStringExtra("profile_pic")).into(profilePic)

        //set data to view
        databaseReference.child("user").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var dataPartner = snapshot.children.find {
                    it.key.toString().equals(partner)
                }
                username.text = dataPartner!!.child("username").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        databaseReference.child("chat").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var chat = snapshot.children.find {
                    if (keyChat != null) {
                        it.hasChild(keyChat!!)
                    } else {
                        it.child("user").value.toString().equals(user) && it.child("partner").value.toString().equals(partner) ||
                                it.child("user").value.toString().equals(partner) && it.child("partner").value.toString().equals(user)
                    }
                }
                for (data in chat!!.child("messages").children) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        sendBtn.setOnClickListener {
            sendMessage(messageInput.text.toString(), keyChat, user, partner)
        }
        backBtn.setOnClickListener {

        }
    }

    private fun sendMessage(text: String, keyChat:String?, user:String, partner: String) {
        databaseReference.child("chat").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var chat = snapshot.children.find {
                    if (keyChat != null) {
                        it.hasChild(keyChat)
                    } else {
                        it.child("user").value.toString().equals(user) && it.child("partner").value.toString().equals(partner) ||
                                it.child("user").value.toString().equals(partner) && it.child("partner").value.toString().equals(user)
                    }
                }
                var chatReference : DatabaseReference? = null
                if (chat == null) {
                    chatReference = databaseReference.child("chat").child((snapshot.childrenCount + 1).toString())
                    chatReference.child("user_1").setValue(user)
                    chatReference.child("user_2").setValue(partner)
                } else {
                    chatReference = databaseReference.child("chat").child((chat.key).toString())
                }
                chatReference.child("messages").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val idNewMess = snapshot.childrenCount.toInt() + 1
                        chatReference.child("messages").child(idNewMess.toString()).child("text").setValue(text)!!
                        chatReference.child("messages").child(idNewMess.toString()).child("sender").setValue(user)!!

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}