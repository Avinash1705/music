package com.avi.musix

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.IBinder
import androidx.core.app.NotificationCompat


class MyService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        createNotification();
        return super.onStartCommand(intent, flags, startId)

    }

    private fun createNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, 0
        )
        val notification: Notification = NotificationCompat.Builder(this, "mediaPlayerChannel")
            .setContentTitle("Media Player")
            .setContentText("Playing in the background")
            .setSmallIcon(R.drawable.music_notes)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(null))
            .setContentIntent(pendingIntent)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.music_notes))
            .addAction(R.drawable.back, "previous", null)
            .addAction(R.drawable.play, "play", null)
            .addAction(R.drawable.next, "next", null)
            .addAction(R.drawable.stop, "exit", null)
            .build()
        startForeground(1, notification)
    }

}

