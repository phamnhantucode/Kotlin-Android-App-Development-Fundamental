package com.learning.agecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.learning.agecalculator.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var calendar = Calendar.getInstance()

        findViewById<Button>(R.id.button).setOnClickListener {
            var year = findViewById<TextInputEditText>(R.id.textInput).text.toString().toIntOrNull()
            Log.i("dd", year.toString())
            if (year != null) {
                findViewById<TextView>(R.id.ageText).setText("Your age is: " + (calendar.get(Calendar.YEAR) - year))
            } else {
                Toast.makeText(this, "Cannot parsing your input", Toast.LENGTH_SHORT).show()
            }
        }
    }
}