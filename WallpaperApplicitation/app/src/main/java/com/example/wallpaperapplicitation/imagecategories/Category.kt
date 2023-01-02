package com.example.wallpaperapplicitation.imagecategories

import android.graphics.Bitmap
import com.example.wallpaperapplicitation.wallpaper.Wallpaper

class Category {
    val name: String
    val image: Bitmap

    constructor(name: String, image: Bitmap) {
        this.name = name
        this.image = image
    }

    val wallpapers : List<Wallpaper>? = null


}