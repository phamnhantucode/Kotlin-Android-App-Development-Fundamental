package com.learning.example.Transition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.ViewSwitcher
import com.learning.example.R

class ImageSwitcherExample : AppCompatActivity() {
    var imagesIds = listOf(R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_background)
    var currentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_switcher_example)

        var imageSwitcher = findViewById<ImageSwitcher>(R.id.imageSwitcher)

        imageSwitcher.setFactory(object : ViewSwitcher.ViewFactory {
            override fun makeView(): View {
                var imageView = ImageView(this@ImageSwitcherExample)
                imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                return imageView
            }
        })
        imageSwitcher.setImageDrawable(resources.getDrawable(imagesIds[currentIndex]))

        var inAnimate: Animation = AnimationUtils.loadAnimation(this@ImageSwitcherExample, android.R.anim.slide_in_left)
        var outAnimate = AnimationUtils.loadAnimation(this@ImageSwitcherExample, android.R.anim.slide_out_right)

        imageSwitcher.inAnimation = inAnimate
        imageSwitcher.outAnimation = outAnimate

        findViewById<Button>(R.id.nextBtn).setOnClickListener {
            currentIndex++
            if (currentIndex == imagesIds.size) currentIndex = 0
            imageSwitcher.setImageDrawable(resources.getDrawable(imagesIds[currentIndex]))
        }
    }
}