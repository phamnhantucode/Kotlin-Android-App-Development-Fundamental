package com.learning.example.Transition

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.learning.example.R
import java.util.zip.Inflater

class CustomAdapterVF : BaseAdapter {
    var listImgIds: List<Int>
    var context: Context
    constructor(context: Context, list: List<Int>) {
        this.listImgIds = list
        this.context = context
    }

    override fun getCount(): Int {
        return listImgIds.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, null)
        view.findViewById<ImageView>(R.id.imageView).setImageResource(listImgIds[position])
        return view
    }
}