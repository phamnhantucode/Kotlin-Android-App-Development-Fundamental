package com.example.chatapp.messages

class MessagesList {
    val username: String
    val lastestMessage: String
    val profilePic: String
    val unseenMessages: Int
    val id: String
    val keyChat = ""


    constructor(username: String, lastestMessage: String, profilePic: String, unseenMessages: Int, id: String) {
        this.username = username
        this.lastestMessage = lastestMessage
        this.profilePic = profilePic
        this.unseenMessages = unseenMessages
        this.id = id
    }
}