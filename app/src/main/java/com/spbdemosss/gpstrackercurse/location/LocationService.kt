package com.spbdemosss.gpstrackercurse.location

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.*
import com.spbdemosss.gpstrackercurse.MainActivity
import com.spbdemosss.gpstrackercurse.R
import org.osmdroid.util.GeoPoint


class LocationService : Service() {
    private var distance = 0.0f
    private var lastLocation: Location? = null
    private lateinit var locProvider: FusedLocationProviderClient
    private lateinit var locRequest: LocationRequest
    private lateinit var geoPointsList: ArrayList<GeoPoint>
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startNotification()
        startLocationUpdates()
        isRunning = true
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        geoPointsList = ArrayList()
        initLocation()
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        locProvider.removeLocationUpdates(locCallBack)
    }

    private val locCallBack = object : LocationCallback() {
        override fun onLocationResult(lResult: LocationResult) {
            super.onLocationResult(lResult)
            val currentLocation = lResult.lastLocation
            if (lastLocation != null && currentLocation != null) {
                distance += lastLocation?.distanceTo(currentLocation)!!
                geoPointsList.add(GeoPoint(currentLocation.latitude, currentLocation.longitude))
                val locModel = LocationModel(
                    currentLocation.speed,
                    distance,
                    geoPointsList
                )
                sendLocData(locModel)
            }
            lastLocation = currentLocation
        }
    }

    private fun sendLocData(LocModel: LocationModel){
        val i = Intent(LOC_MODEL_INTENT)
        i.putExtra(LOC_MODEL_INTENT, LocModel)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(i)
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

    private fun initLocation(){
        var minimalDistance = 3F
        locRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).apply {
            setMinUpdateDistanceMeters(minimalDistance)
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()
        locProvider = LocationServices.getFusedLocationProviderClient(baseContext)

    }

    private fun startLocationUpdates(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return
        locProvider.requestLocationUpdates(
            locRequest,
            locCallBack,
            Looper.myLooper()
        )
    }

    companion object {
        const val LOC_MODEL_INTENT = "loc_intent"
        const val CHANNEL_ID = "channel_1"
        var isRunning = false
        var startTime = 0L
    }
}