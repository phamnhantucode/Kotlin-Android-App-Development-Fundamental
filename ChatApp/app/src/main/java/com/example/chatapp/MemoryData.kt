package com.example.chatapp

import android.content.Context
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class MemoryData {
    companion object {
        fun saveData(data: String, context: Context) {
            var fileOutputStream: FileOutputStream = context.openFileOutput("data.txt", Context.MODE_PRIVATE)
            fileOutputStream.write(data.encodeToByteArray())
            fileOutputStream.close()
        }

        fun getData(context: Context): String {
            var data = ""
            try {
                var fis : FileInputStream = context.openFileInput("data.txt")
                var bufferReader = BufferedReader(InputStreamReader(fis))
                var sb = StringBuilder()
                var line: String? = bufferReader.readLine()
                while (line != null) {
                    sb.append(line)
                    line = bufferReader.readLine()
                }
                data = sb.toString()
            } catch (e:Exception) {
                e.printStackTrace()
            }
            return data
        }
    }

}

