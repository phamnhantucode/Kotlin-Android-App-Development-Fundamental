package com.learning.example

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import java.util.Calendar

class TimePickerApp : AppCompatActivity() {
    var timerPicker: TimePicker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_time_picker)

        timerPicker = findViewById(R.id.simpleTimePicker)
        timerPicker?.currentHour = 5
        timerPicker?.setIs24HourView(true)

        findViewById<Button>(R.id.selectBtn).setOnClickListener {
            Toast.makeText(this@TimePickerApp, "${timerPicker!!.currentHour}:${timerPicker!!.currentMinute}", Toast.LENGTH_LONG).show()

        }

        findViewById<Button>(R.id.openDatePickerDialogBtn).setOnClickListener {
            var calendar = Calendar.getInstance()
            var datePickerDialog = DatePickerDialog(this@TimePickerApp, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    Toast.makeText(this@TimePickerApp, "${dayOfMonth}/${month}/${year}", Toast.LENGTH_SHORT).show()
                }

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()

        }
    }
}