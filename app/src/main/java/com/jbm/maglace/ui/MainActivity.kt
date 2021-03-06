package com.jbm.maglace.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jbm.maglace.R
import com.jbm.maglace.utils.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val TAG: String =  "tag.jbm." + this::class.java.simpleName

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        // prepare to ask for all the needed permissions
        var permissionsArray = arrayListOf<String>()
        permissionsArray.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        permissionsArray.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissionsArray.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        requestPermissionsIfNecessary(permissionsArray)
    }
    //TODO test osm location without permission
    @Override
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.d(TAG, "onRequestPermissionsResult " + permissions.toString())

        if (requestCode == Constants().LOCATION_REQUEST_PERMISSIONS_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                //TODO nothing to do for the moment. Location is handle by osm. But might problematic if no permisson
            else
                Toast.makeText(this, "Please, set location manually in settings", Toast.LENGTH_LONG)
                    .show()
        }
    }

    private fun requestPermissionsIfNecessary(permissions: ArrayList<String>) {
        val permissionsToRequest: ArrayList<String> = ArrayList()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is not granted
                permissionsToRequest.add(permission)
            }
        }

        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toArray(arrayOfNulls(0)),
                Constants().LOCATION_REQUEST_PERMISSIONS_CODE
            )
        }
    }
}