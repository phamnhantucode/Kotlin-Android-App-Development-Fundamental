package com.example.chatapp.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.LayoutInflaterFactory
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.MyViewHolder>{
    var list: List<ChatList>
    val context: Context

    constructor(list: List<ChatList>, context: Context) : super() {
        this.list = list
        this.context = context
    }



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageUser: TextView
        val messagePartner: TextView
        init {
            messageUser = itemView.findViewById(R.id.messageUser)
            messagePartner = itemView.findViewById(R.id.messagePartner)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.messages_adapter_layout, null))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item = list[position]
        if (item.isUser) {
            holder.messageUser.text = item.text
            holder.messagePartner.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}