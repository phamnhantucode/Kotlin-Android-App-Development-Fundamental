package com.learning.example

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

    }
    fun exit(v: View) {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Xác nhận thoát!!")
        builder.setMessage("Thoát?")
        builder.setIcon(R.drawable.ic_launcher_foreground)
        builder.setCancelable(false)

        builder.setNeutralButton("Đồng ý", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                finish();
            }

        })
        builder.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                var v = layoutInflater.inflate(R.layout.custom_toast, findViewById(R.id.toast_layout_root))
                var toast = Toast(this@MainActivity)
                toast.view = v
                toast.duration = Toast.LENGTH_LONG
                toast.show()

            }

        })

        var alertDialog = builder.create()
        alertDialog.show()
    }
}