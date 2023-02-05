package com.learning.example.Transition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.ViewAnimator
import com.learning.example.R

class ViewAnimatorExample : AppCompatActivity() {
    var currentIndex = 0;
    var imageIds = listOf(R.drawable.android_icon, R.drawable.java_icon, R.drawable.background)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_view_animator)

        val inAnimator = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        val outAnimator = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)

        val viewAnimator = findViewById<ViewAnimator>(R.id.viewAnimator)
        for (id in imageIds) {
            val img = ImageView(this)
            img.setImageResource(id)
            viewAnimator.addView(img)
        }
//        viewAnimator.animateFirstView = false
        viewAnimator.setInAnimation(inAnimator)
        viewAnimator.setOutAnimation(outAnimator)



        findViewById<Button>(R.id.nextBtn).setOnClickListener {
            viewAnimator.showNext()
        }
        findViewById<Button>(R.id.previousBtn).setOnClickListener {
            viewAnimator.showPrevious()
        }
    }
}