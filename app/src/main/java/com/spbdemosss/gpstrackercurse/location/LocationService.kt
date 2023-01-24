package com.spbdemosss.gpstrackercurse.location

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.spbdemosss.gpstrackercurse.MainActivity
import com.spbdemosss.gpstrackercurse.R


class LocationService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startNotification()
        isRunning = true
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    private fun startNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nChannel = NotificationChannel(
                CHANNEL_ID,
                "Location Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val nManager = getSystemService(NotificationManager::class.java) as NotificationManager
            nManager.createNotificationChannel(nChannel)
        }

        val nIntent = Intent(this, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(
            this,
            777,
            nIntent,
            0
        )
        val notification = NotificationCompat.Builder(
            this,
            CHANNEL_ID
        )
            .setContentText("Common Text")
            .setContentTitle("Tracker Running!")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pIntent)
            .build()
        startForeground(7777, notification)
    }

    companion object {
        const val CHANNEL_ID = "channel_1"
        var isRunning = false
    }
}