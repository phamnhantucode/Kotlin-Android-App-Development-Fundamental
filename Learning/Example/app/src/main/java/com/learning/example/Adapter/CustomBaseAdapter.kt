package com.learning.example.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.learning.example.R

class CustomBaseAdapter : BaseAdapter {

    val contex: Context
    val imgs: List<Int>
    val inflater: LayoutInflater

    constructor(context: Context, imgs: List<Int>) : super() {
        this.contex = context
        this.imgs = imgs
        inflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return imgs.size
    }

    override fun getItem(position: Int): Any {
        return imgs[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.grid_view_item, null)
        view.findViewById<ImageView>(R.id.imageView).setImageResource(imgs[position])
        return view

    }
}