package com.learning.example.Adapter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import com.learning.example.R

class SimpleAdapterExample : AppCompatActivity() {
    val from = arrayOf("image", "name")
    val to = intArrayOf(R.id.imageView, R.id.textView)
    val imgNames = listOf("Android icon", "Java icon", "Background")
    val imgIds = listOf(R.drawable.android_icon, R.drawable.java_icon, R.drawable.background)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_adapter_example)
        var arrayList = ArrayList<HashMap<String, String>>()
        for (i in imgNames.indices) {
            var hashMap = HashMap<String, String>()
            hashMap.put("image", imgIds[i].toString())
            hashMap.put("name", imgNames[i])
            arrayList.add(hashMap)
        }

        findViewById<ListView>(R.id.listView).adapter = CustomSimpleAdapter(this, arrayList,R.layout.simple_adapter_items, from , to, this)
    }

}

class CustomSimpleAdapter: SimpleAdapter {
    val context: Context
    val arrayList: ArrayList<HashMap<String, String>>
    constructor(
        context: Context?,
        data: MutableList<out MutableMap<String, *>>?,
        resource: Int,
        from: Array<out String>?,
        to: IntArray?,
        context1: Context
    ) : super(context, data, resource, from, to) {
        this.context = context1
        this.arrayList = data as ArrayList<HashMap<String, String>>
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var v = super.getView(position, convertView, parent)
        v.setOnClickListener {
            Toast.makeText(context, arrayList[position]["name"], Toast.LENGTH_SHORT).show()
        }
        return v
    }
}