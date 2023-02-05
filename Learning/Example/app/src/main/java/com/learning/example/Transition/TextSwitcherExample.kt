package com.learning.example.Transition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextSwitcher
import android.widget.TextView
import android.widget.ViewSwitcher
import com.learning.example.R
import org.w3c.dom.Text

class TextSwitcherExample : AppCompatActivity() {
    var currentTextId = 0;
    var texts = listOf<String>("Hello", "How are you")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_switcher_example)

        var textSwitcherExample = findViewById<TextSwitcher>(R.id.textSwitcherExample)
        var inAnimate = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        var outAnimate = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)

        textSwitcherExample.setFactory(object : ViewSwitcher.ViewFactory {
            override fun makeView(): View {
                var t = TextView(this@TextSwitcherExample)
                t.textSize = 30F
                t.gravity = (Gravity.CENTER_HORIZONTAL)
                return t
            }
        })

        textSwitcherExample.setInAnimation(inAnimate)
        textSwitcherExample.setOutAnimation(outAnimate)

        textSwitcherExample.setCurrentText(texts[currentTextId])

        findViewById<Button>(R.id.nextBtn).setOnClickListener {
            currentTextId++
            when {
                currentTextId < texts.size -> {
                    textSwitcherExample.setCurrentText(texts[currentTextId])
                }
                currentTextId == texts.size -> {
                    textSwitcherExample.setCurrentText(texts[0])
                    currentTextId = 0
                }
            }
        }
    }
}