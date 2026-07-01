package com.example.ecommerceshop.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import java.util.Locale

class LocationHelper(
    private val context: Context
) {

    interface LocationCallback {
        fun onLocationReceived(location: String)
        fun onLocationFailed(message: String)
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(callback: LocationCallback) {

        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            callback.onLocationFailed("Permission not granted")
            return
        }

        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->

                if (location != null) {

                    val geocoder = Geocoder(
                        context,
                        Locale.getDefault()
                    )

                    try {

                        val addresses = geocoder.getFromLocation(
                            location.latitude,
                            location.longitude,
                            1
                        )

                        if (!addresses.isNullOrEmpty()) {

                            val city =
                                addresses[0].locality
                                    ?: addresses[0].subAdminArea
                                    ?: addresses[0].adminArea
                                    ?: "Unknown"

                            callback.onLocationReceived(city)

                        } else {

                            callback.onLocationFailed("Location not found")

                        }

                    } catch (e: Exception) {

                        callback.onLocationFailed(e.message ?: "Error")

                    }

                } else {

                    callback.onLocationFailed("Location unavailable")

                }

            }
            .addOnFailureListener {

                callback.onLocationFailed(it.message ?: "Error")

            }

    }

}