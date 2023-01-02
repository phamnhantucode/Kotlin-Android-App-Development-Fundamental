package com.example.musicplayerapp

import android.net.Uri

class MusicList {
    var title: String
    var artist: String
    var duration: String

    var isPlaying: Boolean

    var musicFile: Uri

    constructor(
        title: String,
        artist: String,
        duration: String,
        isPlaying: Boolean,
        musicFile: Uri
    ) {
        this.title = title
        this.artist = artist
        this.duration = duration
        this.isPlaying = isPlaying
        this.musicFile = musicFile
    }


}