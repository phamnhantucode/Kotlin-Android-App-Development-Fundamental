package com.learning.animalcrossingsimpleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    var diem = 100
    var txtDiem : TextView? = null
    var cb1 : CheckBox? = null
    var cb2 : CheckBox? = null
    var cb3 : CheckBox? = null

    var sk1 : SeekBar? = null
    var sk2 : SeekBar? = null
    var sk3 : SeekBar? = null

    var ibtnPlay : ImageButton? = null
    var countDownTimer = object: CountDownTimer(60000, 300) {
        override fun onTick(millisUntilFinished: Long) {
            var road = 5
            var random = java.util.Random()
            sk1!!.progress += random.nextInt(road)
            sk2!!.progress += random.nextInt(road)
            sk3!!.progress += random.nextInt(road)
            when {
                sk1!!.progress == sk1!!.max -> {
                    Toast.makeText(this@MainActivity, "Pikachu win", Toast.LENGTH_SHORT).show()
                    this.cancel()
                    if (cb1!!.isChecked) {
                        diem += 10
                    } else diem -= 5
                    this.onFinish()
                }
                sk2!!.progress == sk2!!.max -> {
                    Toast.makeText(this@MainActivity, "Squirtle win", Toast.LENGTH_SHORT).show()
                    this.cancel()
                    if (cb2!!.isChecked) {
                        diem += 10
                    } else diem -= 5
                    this.onFinish()
                }
                sk3!!.progress == sk3!!.max -> {
                    Toast.makeText(this@MainActivity, "Lapras win", Toast.LENGTH_SHORT).show()
                    this.cancel()
                    if (cb3!!.isChecked) {
                        diem += 10
                    } else diem -= 5
                    this.onFinish()
                }
            }

        }

        override fun onFinish() {
            txtDiem?.text = diem.toString()
            ibtnPlay?.visibility = View.VISIBLE
            cb1!!.isEnabled = true
            cb2!!.isEnabled = true
            cb3!!.isEnabled = true
            sk1!!.isEnabled = true
            sk2!!.isEnabled = true
            sk3!!.isEnabled = true
        }



    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mappingView()
    }

    private fun mappingView() {
        txtDiem = findViewById(R.id.score)
        txtDiem?.text = diem.toString()
        ibtnPlay = findViewById(R.id.ibtnPlay)

        ibtnPlay?.setOnClickListener {
            if (cb1!!.isChecked || cb2!!.isChecked || cb3!!.isChecked) {
                cb1!!.isEnabled = false
                cb2!!.isEnabled = false
                cb3!!.isEnabled = false
                sk1!!.isEnabled = false
                sk2!!.isEnabled = false
                sk3!!.isEnabled = false
                sk1?.progress = 0
                sk2?.progress = 0
                sk3?.progress = 0
                ibtnPlay!!.visibility = View.INVISIBLE
                countDownTimer.start()
            } else {
                Toast.makeText(this@MainActivity, "Vui long chon vi tri dat cuoc", Toast.LENGTH_SHORT ).show()
            }
        }

        cb1 = findViewById(R.id.checkbox1)
        cb1?.setOnCheckedChangeListener { buttonView, isChecked ->
            cb2!!.isChecked = false
            cb3!!.isChecked = false
        }
        cb2 = findViewById(R.id.checkbox2)
        cb2?.setOnCheckedChangeListener { buttonView, isChecked ->
            cb1!!.isChecked = false
            cb3!!.isChecked = false
        }

        cb3 = findViewById(R.id.checkbox3)
        cb3?.setOnCheckedChangeListener { buttonView, isChecked ->
            cb1!!.isChecked = false
            cb2!!.isChecked = false
        }
        sk1 = findViewById(R.id.seekbar1)
        sk2 = findViewById(R.id.seekbar2)
        sk3 = findViewById(R.id.seekbar3)
    }


}