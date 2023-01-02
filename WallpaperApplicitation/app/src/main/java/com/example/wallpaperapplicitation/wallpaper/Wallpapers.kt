package com.example.wallpaperapplicitation.wallpaper

import android.app.ProgressDialog
import android.content.Intent
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapplicitation.MainActivity
import com.example.wallpaperapplicitation.R
import java.io.InputStream
import kotlin.concurrent.thread

class Wallpapers : AppCompatActivity() {
    var wallpaperList: MutableList<Wallpaper> = ArrayList<Wallpaper>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpapers)
        val categoryName = intent.getStringExtra("category")
        findViewById<TextView>(R.id.category).text = categoryName
        val wallpapersRecyclerView = findViewById<RecyclerView>(R.id.wallpapers)
        wallpapersRecyclerView.setHasFixedSize(true)
        wallpapersRecyclerView.layoutManager = GridLayoutManager(this@Wallpapers, 2)
        val progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        thread {
            when (categoryName) {
                "Nature" -> {
                    wallpaperList.add(Wallpaper(BitmapFactory.decodeStream(assets.open("nature1.jpg")), "nature1.jpg"))
                    wallpaperList.add(Wallpaper(BitmapFactory.decodeStream(assets.open("nature2.jpg")), "nature2.jpg"))
                }
                "City" -> {
                    wallpaperList.add(Wallpaper(BitmapFactory.decodeStream(assets.open("city1.jpg")), "city1.jpg"))
                    wallpaperList.add(Wallpaper(BitmapFactory.decodeStream(assets.open("city2.jpg")), "city2.jpg"))
                }
                "Planet" -> {
                    wallpaperList.add(Wallpaper(BitmapFactory.decodeStream(assets.open("planet1.jpg")), "planet1.jpg"))
                    wallpaperList.add(Wallpaper(BitmapFactory.decodeStream(assets.open("planet2.jpg")), "planet2.jpg"))
                }
            }

            runOnUiThread {
                progressDialog.dismiss()
                wallpapersRecyclerView.adapter = WallpaperAdapter(wallpaperList, this@Wallpapers)
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@Wallpapers, MainActivity::class.java))
        finish()
    }
}