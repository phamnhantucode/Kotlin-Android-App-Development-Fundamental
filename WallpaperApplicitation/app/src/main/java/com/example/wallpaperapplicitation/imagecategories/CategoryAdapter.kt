package com.example.wallpaperapplicitation.imagecategories

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapplicitation.R
import com.example.wallpaperapplicitation.wallpaper.Wallpapers

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    val list : MutableList<Category>
    val context: Context

    constructor(list: MutableList<Category>, context: Context) : super() {
        this.list = list
        this.context = context
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName : TextView
        val categoryImage: ImageView

        init {
            categoryImage = itemView.findViewById(R.id.categoryImage)
            categoryName = itemView.findViewById(R.id.categoryName)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_adapter, null))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category: Category = list[position]
        holder.categoryImage.setImageBitmap(category.image)
        holder.categoryName.text = category.name
        holder.categoryImage.setOnClickListener {
            context.startActivity(Intent(context, Wallpapers::class.java).putExtra("category", category.name))
            (context as AppCompatActivity).finish()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}