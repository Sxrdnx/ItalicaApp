package com.example.mylocation.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.example.mylocation.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date

class LocService : Service() {
    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var firestore: FirebaseFirestore
    private val LOCATION_UPDATE_INTERVAL = 120000 // 2 minutes in milliseconds
    private val NOTIFICATION_ID = 123 // Notification ID
    override fun onCreate() {
        super.onCreate()
        locationClient = LocationServices.getFusedLocationProviderClient(this)
        firestore = FirebaseFirestore.getInstance()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService()
        startLocationUpdates()
        return START_STICKY
    }

    private fun startForegroundService() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)
            val channelId = "location_service_channel"
            val channel = NotificationChannel(
                channelId,
                "Location Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)

            val notification = NotificationCompat.Builder(this, channelId)
                .setContentTitle("Location Service")
                .setContentText("Sending location updates to Firestore")
                .setSmallIcon(R.drawable.android_icon)
                .build()

            startForeground(NOTIFICATION_ID, notification)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(LOCATION_UPDATE_INTERVAL.toLong())
            .setFastestInterval(LOCATION_UPDATE_INTERVAL.toLong())

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                uploadLocationToFirestore(location!!)
            }
        }
        locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    private fun uploadLocationToFirestore(location: Location) {
        val locationData = hashMapOf(
            "latitude" to location.latitude,
            "longitude" to location.longitude,
            "timestamp" to getCurrentDateTime()

        )

        firestore.collection("locations")
            .add(locationData)
            .addOnSuccessListener { documentReference ->
                // Location data uploaded successfully
            }
            .addOnFailureListener { e ->
                // Handle upload failure
            }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun getCurrentDateTime(): String {
        val currentTimeMillis = System.currentTimeMillis()
        val date = Date(currentTimeMillis)
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        return sdf.format(date)
    }
}