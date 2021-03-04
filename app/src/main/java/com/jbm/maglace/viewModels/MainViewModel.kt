package com.jbm.maglace.viewModels

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationServices
import com.jbm.maglace.model.MyRepository
import com.jbm.maglace.model.Rink
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.round
import kotlin.math.roundToInt

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val myRepository: MyRepository): ViewModel()
{
    val TAG: String =  "tag.jbm." + this::class.java.simpleName

    var liveRinkList = MutableLiveData<MutableList<Rink>>()
    var livePostion = MutableLiveData<Location>()

    init {
        myRepository.getRinksFromURL {
            var rinkList = mutableListOf<Rink>()

            for (district in it) {
                for (rink in district.patinoires) {

                    rink.distFromUser = getDistanceFromUser(rink)

                    rinkList.add(rink)
                }
            }

            rinkList.sortBy { it.distFromUser }

            liveRinkList.postValue(rinkList)
        }
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation () {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation.addOnSuccessListener { userLocation ->
                if (userLocation != null) {
                    Log.d(TAG, "Last Location received = " + userLocation.latitude + " " + userLocation.longitude)
                    livePostion.postValue(userLocation)
                }
            }
    }

    fun getDistanceFromUser(rink: Rink): Float {
        val rinkLocation = Location("rinkDestination")
        rinkLocation.longitude = rink.lng
        rinkLocation.latitude = rink.lat

        var dist = livePostion.value!!.distanceTo(rinkLocation)
        dist = (round(dist/1000 * 100) /100)

        return dist
    }
}