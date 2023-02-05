package com.learning.example.Adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import com.learning.example.R

class BaseAdapterExample : AppCompatActivity() {
    val imgs = listOf<Int>(R.drawable.android_icon, R.drawable.java_icon, R.drawable.background)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_adapter_example)

        findViewById<GridView>(R.id.gridView).adapter = CustomBaseAdapter(this, imgs)
    }
}