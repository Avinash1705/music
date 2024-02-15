package com.avi.musix

import android.app.ActivityManager
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var mediaPlayerMain: MediaPlayer
    lateinit var btnPlayPause: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MyNotification.createNotificationChannel(this)

        mediaPlayerMain = MediaPlayer.create(
            this,
           R.raw.song
        ); // Replace with your music file

        btnPlayPause = findViewById<Button>(R.id.btnPlayPause);

        btnPlayPause.setOnClickListener {
            var mediaPlayer = mediaPlayerMain
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause();
                btnPlayPause.text = "Play";
            } else {
                mediaPlayer.start();
                btnPlayPause.text = "Pause";
                //showing notification
                startService(Intent(this, MyService::class.java))
                if (isMyServiceRunning(MyService::class.java)) {
                    Log.d("service status", "background: ")
                } else {
                    Log.d("service status", "not running  ")
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayerMain != null) {
            mediaPlayerMain.release()
            stopService(Intent(this, MyService::class.java))
            if (isMyServiceRunning(MyService::class.java)) {
                Log.d("service status", "background: ")
            } else {
                Log.d("service status", "not running  ")
            }
        }
        MyNotification.removeNotification(this)
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}