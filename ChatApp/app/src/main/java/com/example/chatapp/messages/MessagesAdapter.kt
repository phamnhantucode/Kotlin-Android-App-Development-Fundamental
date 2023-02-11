package com.example.chatapp.messages

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.MainActivity
import com.example.chatapp.R
import com.example.chatapp.chat.Chat
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class MessagesAdapter : RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {
    val context: Context
    val list: MutableList<MessagesList>

    constructor(context: Context, list: MutableList<MessagesList>) : super() {
        this.context = context
        this.list = list
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profilePic: CircleImageView
        val username: TextView
        val lastestMessage: TextView
        val usseenMessage: ImageView
        val rootLayout: RelativeLayout

        init {
            profilePic = itemView.findViewById(R.id.profilePic)
            username = itemView.findViewById(R.id.username)
            lastestMessage = itemView.findViewById(R.id.lastestMessage)
            usseenMessage = itemView.findViewById(R.id.unseenMessage)
            rootLayout = itemView.findViewById(R.id.rootLayout)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.messages_adapter_layout, null))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val messagesList = list[position]
        Picasso.get().load(messagesList.profilePic).into(holder.profilePic)
        holder.username.text = messagesList.username
        holder.lastestMessage.text = messagesList.lastestMessage

        if (messagesList.unseenMessages == 0) {
            holder.usseenMessage.visibility = View.GONE
        } else holder.usseenMessage.visibility = View.VISIBLE

        holder.rootLayout.setOnClickListener {
            var intent = Intent(context, Chat::class.java)
            intent.putExtra("user", (context as MainActivity).key)
            intent.putExtra("partner", messagesList.id)
            intent.putExtra("key_chat", messagesList.keyChat)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}