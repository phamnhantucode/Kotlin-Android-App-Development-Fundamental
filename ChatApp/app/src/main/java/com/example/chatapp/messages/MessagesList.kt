package com.example.chatapp.messages

class MessagesList {
    val username: String
    val lastestMessage: String
    val profilePic: String
    val unseenMessages: Int

    constructor(username: String, lastestMessage: String, profilePic: String, unseenMessages: Int) {
        this.username = username
        this.lastestMessage = lastestMessage
        this.profilePic = profilePic
        this.unseenMessages = unseenMessages
    }
}