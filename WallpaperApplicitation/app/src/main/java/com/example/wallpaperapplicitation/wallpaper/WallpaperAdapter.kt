package com.example.wallpaperapplicitation.wallpaper

import android.app.WallpaperManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapplicitation.R

class WallpaperAdapter : RecyclerView.Adapter<WallpaperAdapter.MyViewHolder>{
    val list: MutableList<Wallpaper>
    val context: Context

    constructor(list: MutableList<Wallpaper>, context: Context) : super() {
        this.list = list
        this.context = context
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView
        init {
            image = itemView.findViewById(R.id.wallpaper)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.wallpaper_adapter, null))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val wallpaper = list[position]
        holder.image.setImageBitmap(wallpaper.image)
        holder.image.setOnClickListener {
            WallpaperManager.getInstance(context).setBitmap(wallpaper.image)
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}