package com.example.chatapp.chat

class ChatList {
    val text: String
    val isUser: Boolean

    constructor(text: String, isUser: Boolean) {
        this.text = text
        this.isUser = isUser
    }
}