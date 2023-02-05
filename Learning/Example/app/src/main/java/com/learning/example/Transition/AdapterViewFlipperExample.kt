package com.learning.example.Transition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.AdapterViewFlipper
import com.learning.example.R

class AdapterViewFlipperExample : AppCompatActivity() {
    var list = listOf<Int>(R.drawable.java_icon, R.drawable.background, R.drawable.android_icon)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adapter_view_flipper_example)

        val adapterViewFlipper = findViewById<AdapterViewFlipper>(R.id.adapterViewFlipperExample)
        adapterViewFlipper.flipInterval = 2000
        adapterViewFlipper.isAutoStart = true
        adapterViewFlipper.adapter = CustomAdapterVF(this, list)
    }
}