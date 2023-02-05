package com.learning.example.Transition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ViewSwitcher
import com.learning.example.R

class ViewSwitcherExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_switcher_example)

        val inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        val outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)

        val viewSwitcher = findViewById<ViewSwitcher>(R.id.viewSwitcherExample)
        viewSwitcher.setInAnimation(inAnimation)
        viewSwitcher.setOutAnimation(outAnimation)
    }

    fun nextView(v: View) {
        (v as ViewSwitcher).showNext()
    }
}