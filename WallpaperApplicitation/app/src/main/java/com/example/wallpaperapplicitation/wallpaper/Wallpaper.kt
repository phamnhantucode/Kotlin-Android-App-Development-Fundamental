package com.example.wallpaperapplicitation.wallpaper

import android.graphics.Bitmap

class Wallpaper {
    val image: Bitmap
    val fileName: String

    constructor(image: Bitmap, fileName: String) {
        this.image = image
        this.fileName = fileName
    }
}