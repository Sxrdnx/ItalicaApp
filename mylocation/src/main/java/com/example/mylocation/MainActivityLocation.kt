package com.example.mylocation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mylocation.common.PermissionRequester
import com.example.mylocation.databinding.ActivityMainLocationBinding
import com.example.mylocation.service.LocService
import com.example.mylocation.ui.LocationsAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class MainActivityLocation : AppCompatActivity() {

    private lateinit var mainLocationBinding: ActivityMainLocationBinding
    val serviceIntent: Intent by lazy { Intent(this, LocService::class.java) }
    val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private lateinit var adapter: LocationsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendLocationPeriodically()
        mainLocationBinding = ActivityMainLocationBinding.inflate(layoutInflater)
        adapter = LocationsAdapter()
        setContentView(mainLocationBinding.root)
        mainLocationBinding.rcyDataList.adapter = adapter
        mainLocationBinding.buttonStop.setOnClickListener {
            stopService(serviceIntent)
        }
        mainLocationBinding.buttonUpdate.setOnClickListener {
            getLocations()
        }
    }

    private fun getLocations() {
        CoroutineScope(Dispatchers.IO).launch {
            val locationsRef = db.collection("locations").get().await()
            if (!locationsRef.isEmpty) {
                val listLocation = locationsRef.map {
                    val latitude = it.data?.get("latitude") ?: "none latitud"
                    val longitude = it.data?.get("longitude") ?: "none longitud"
                    val date = it.data?.get("timestamp") ?: "none date"
                    " latitude: $latitude, longitude: $longitude, date: $date"
                }
                runOnUiThread {
                    adapter.submitList(listLocation)
                }

            }
        }
    }

    private fun sendLocationPeriodically() {
        PermissionRequester(
            this@MainActivityLocation, android.Manifest.permission.ACCESS_FINE_LOCATION
        ).request { permission ->
            if (permission) {
                startService(serviceIntent)
            }
        }

    }

    override fun onPause() {
        super.onPause()
        startService(serviceIntent)
    }

}