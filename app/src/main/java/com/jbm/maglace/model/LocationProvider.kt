package com.jbm.maglace.model

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jbm.maglace.MainActivity
import com.jbm.maglace.utils.Constants
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject


class LocationProvider (val context: Context) {

    val TAG: String =  "tag.jbm." + this::class.java.simpleName

    var fusedLocationClient: FusedLocationProviderClient

    init {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    fun getLocation() {
        if (!checkPermissions()) {
            showLocationPermissionRequest()
        } else {
            getLastLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation
            .addOnCompleteListener { taskLocation ->
                if (taskLocation.isSuccessful && taskLocation.result != null) {

                    val location = taskLocation.result

                    Log.d(TAG, "getLastLocation success " + location.toString())

                } else {
                    Log.d(TAG, "getLastLocation failed " + taskLocation.exception)
                }
            }
    }

    private fun checkPermissions() =
        ActivityCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun showLocationPermissionRequest() {
        ActivityCompat.requestPermissions(context as MainActivity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            Constants().LOCATION_REQUEST_PERMISSIONS_CODE)
    }
}