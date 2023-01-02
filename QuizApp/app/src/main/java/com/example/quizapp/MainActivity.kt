package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var selectedTopicName = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val java = findViewById<LinearLayout>(R.id.java)

        val php = findViewById<LinearLayout>(R.id.php)
        val html = findViewById<LinearLayout>(R.id.html)
        val android = findViewById<LinearLayout>(R.id.android)

        val startBtn = findViewById<Button>(R.id.startQuizBtn)


        java.setOnClickListener {
            selectedTopicName = "java"
            java.setBackgroundResource(R.drawable.round_back_10_stroke_3)
            php.setBackgroundResource(R.drawable.round_back_10)
            html.setBackgroundResource(R.drawable.round_back_10)
            android.setBackgroundResource(R.drawable.round_back_10)

        }

        php.setOnClickListener {
            selectedTopicName = "php"
            java.setBackgroundResource(R.drawable.round_back_10)
            php.setBackgroundResource(R.drawable.round_back_10_stroke_3)
            html.setBackgroundResource(R.drawable.round_back_10)
            android.setBackgroundResource(R.drawable.round_back_10)
        }

        html.setOnClickListener {
            selectedTopicName = "html"
            java.setBackgroundResource(R.drawable.round_back_10)
            php.setBackgroundResource(R.drawable.round_back_10)
            html.setBackgroundResource(R.drawable.round_back_10_stroke_3)
            android.setBackgroundResource(R.drawable.round_back_10)

        }
        android.setOnClickListener {
            selectedTopicName = "android"
            java.setBackgroundResource(R.drawable.round_back_10)
            php.setBackgroundResource(R.drawable.round_back_10)
            html.setBackgroundResource(R.drawable.round_back_10)
            android.setBackgroundResource(R.drawable.round_back_10_stroke_3)

        }

        startBtn.setOnClickListener {
            if (selectedTopicName.isEmpty()) {
                Toast.makeText(this@MainActivity, "Please select the topic", Toast.LENGTH_SHORT).show()
            } else {
                var intent = Intent(this@MainActivity, QuizActivity::class.java)

                intent.putExtra("selectedTopicName", selectedTopicName)
                startActivity(intent )
            }
        }
    }
}