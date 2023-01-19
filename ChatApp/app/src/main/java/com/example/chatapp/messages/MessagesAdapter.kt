package com.example.chatapp.messages

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text
import java.util.zip.Inflater

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

        init {
            profilePic = itemView.findViewById(R.id.profilePic)
            username = itemView.findViewById(R.id.username)
            lastestMessage = itemView.findViewById(R.id.lastestMessage)
            usseenMessage = itemView.findViewById(R.id.unseenMessage)
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

    }

    override fun getItemCount(): Int {
        return list.size
    }
}