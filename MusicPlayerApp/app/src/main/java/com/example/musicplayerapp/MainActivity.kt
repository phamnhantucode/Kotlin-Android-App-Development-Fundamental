package com.example.musicplayerapp

import MusicAdapter
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.AudioManager
import android.media.Image
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.TimerTask
import java.util.jar.Manifest
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), SongChangeListener {
    val musicLists:MutableList<MusicList> = ArrayList<MusicList>()
    var isPlaying:Boolean = false

    lateinit var musicRecyclerView: RecyclerView
    lateinit var mediaPlayer: MediaPlayer
    lateinit var startTime : TextView
    lateinit var endTime : TextView
    lateinit var seekBar: SeekBar
    lateinit var playPauseImg: ImageView
    lateinit var timer: Timer
    var currentPosition = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var decorView = window.decorView

        var options: Int = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        decorView.systemUiVisibility = options
        setContentView(R.layout.activity_main)



        val searchBtn: LinearLayout = findViewById(R.id.searchBtn)
        val menuBtn: LinearLayout = findViewById(R.id.menuBtn)
        musicRecyclerView = findViewById(R.id.musicRecycleView)
        val playPauseCard: CardView = findViewById(R.id.playPauseCard)
        playPauseImg = findViewById(R.id.playPauseImg)
        val previousBtn: ImageView = findViewById(R.id.previousBtn)
        val nextBtn: ImageView = findViewById(R.id.nextBtn)
        startTime = findViewById(R.id.startTime)
        endTime = findViewById(R.id.endTime)
        seekBar = findViewById(R.id.playerSeekBar)

        musicRecyclerView.setHasFixedSize(true)
        musicRecyclerView.layoutManager = LinearLayoutManager(this)

        mediaPlayer = MediaPlayer()

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            getMusicFiles();
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)  {
                requestPermissions(arrayOf<String>(android.Manifest.permission.READ_EXTERNAL_STORAGE), 11)
            } else {
                getMusicFiles()
            }
        }

        playPauseCard.setOnClickListener {
            if (isPlaying) {
                isPlaying = false
                mediaPlayer.pause()
                playPauseImg.setImageResource(R.drawable.play_icon)
            } else {
                isPlaying = true
                mediaPlayer.start()
                playPauseImg.setImageResource(R.drawable.pause_icon)
            }
        }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    if (isPlaying) {
                        mediaPlayer.seekTo(progress)
                    } else {
                        mediaPlayer.seekTo(0)
                    }
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        previousBtn.setOnClickListener {
            (musicRecyclerView.adapter as MusicAdapter).list.get(currentPosition).isPlaying = false
            if (currentPosition == 0) {
                currentPosition = musicLists.lastIndex
            } else {
                currentPosition -= 1
            }

            (musicRecyclerView.adapter as MusicAdapter).list.get(currentPosition).isPlaying = true
            (musicRecyclerView.adapter as MusicAdapter).notifyDataSetChanged()
        }

        nextBtn.setOnClickListener {
            (musicRecyclerView.adapter as MusicAdapter).list.get(currentPosition).isPlaying = false
            if (currentPosition == musicLists.size -1) {
                currentPosition = 0
            } else {
                currentPosition += 1
            }

            (musicRecyclerView.adapter as MusicAdapter).list.get(currentPosition).isPlaying = true
            (musicRecyclerView.adapter as MusicAdapter).notifyDataSetChanged()
        }

    }

    @SuppressLint("Range")
    private fun getMusicFiles() {

        var contentResolver: ContentResolver = getContentResolver()
        var uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        var cursor: Cursor? = contentResolver.query(uri, null, MediaStore.Audio.Media.DATA + " LIKE?" , arrayOf<String>("%.mp3%"), null)
        if (cursor == null) {
            Toast.makeText(this, "Something went wrong!!!", Toast.LENGTH_SHORT).show()
        } else if (!cursor.moveToNext()) {
            Toast.makeText(this, "No Music found", Toast.LENGTH_SHORT).show()
        } else {
            while(cursor.moveToNext()) {
                val getMusicFileName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                val getArtistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))

                var cursorId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID))

                var musicFileUri: Uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cursorId)
                var getDuration = "0"

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)) != null) {
                        getDuration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    }
                }

                if (getDuration.toInt() > 0) {
                    val musicList = MusicList(getMusicFileName,getArtistName, getDuration, false, musicFileUri)
                    musicLists.add(musicList)
                }

            }
            if (musicRecyclerView != null) {
                musicRecyclerView.adapter = MusicAdapter(musicLists, this@MainActivity)
            }
        }
        if (cursor != null) {
            cursor.close()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getMusicFiles()
        } else {
            Toast.makeText(this, "Permission Declined by User", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onChange(position: Int) {
        currentPosition = position
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            mediaPlayer.reset()
        }

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

        Thread(Runnable {
            try {
                mediaPlayer.setDataSource(this@MainActivity, musicLists.get(position).musicFile)
                mediaPlayer.prepare()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Unable to plat track", Toast.LENGTH_SHORT).show()
            }
        }).start()

        mediaPlayer.setOnPreparedListener {
            val getTotalDuration =  it.duration.toLong()
            var generateDuration = String.format(
                Locale.getDefault(), "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(getTotalDuration),
                TimeUnit.MILLISECONDS.toSeconds(getTotalDuration)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(getTotalDuration)))

            endTime.setText(generateDuration)
            isPlaying = true
            seekBar.max = getTotalDuration.toInt()
            playPauseImg.setImageResource(R.drawable.pause_icon)
            it.start()
        }

        timer = Timer()

        timer.scheduleAtFixedRate(object: TimerTask() {
            override fun run() {
                runOnUiThread(Runnable {
                    val getCurrentDuration: Long = mediaPlayer.currentPosition.toLong();
                    var generateDuration = String.format(
                        Locale.getDefault(), "%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(getCurrentDuration),
                        TimeUnit.MILLISECONDS.toSeconds(getCurrentDuration)
                                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(getCurrentDuration)))

                    seekBar.setProgress(getCurrentDuration.toInt())

                    startTime.setText(generateDuration)
                })

            }

        }, 1000, 1000)

        mediaPlayer.setOnCompletionListener {
            it.reset()

            timer.purge()
            timer.cancel()

            isPlaying = false

            playPauseImg.setImageResource(R.drawable.play_icon)
            seekBar.progress = 0

        }



    }


}