package com.jbm.maglace.ui

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jbm.maglace.model.MyRepository
import com.jbm.maglace.model.Rink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class MainViewModel @Inject constructor(
    val myRepository: MyRepository): ViewModel()
{
    val TAG: String =  "tag.jbm." + this::class.java.simpleName

    var liveRinkList = MutableLiveData<MutableList<Rink>>()
    var liveRink = MutableLiveData<Rink>()

    init {
        myRepository.getRinksFromURL {
            var rinkList = mutableListOf<Rink>()

            for (district in it) {
                for (rink in district.patinoires) {
                    rinkList.add(rink)
                }
            }

            rinkList.sortBy { it.distFromUser }

            setFirstRinkList(rinkList)
        }
    }

    fun setFirstRinkList(rinkList: MutableList<Rink>) {
        setRinkList(rinkList)
        updateDistanceFormUser()
    }

    fun setRinkList(rinkList: MutableList<Rink>) {
        myRepository.rinkList = rinkList
        liveRinkList.postValue(rinkList)
    }

    fun setFirstLocation(location: Location) {
        myRepository.lastLocation = location
        updateDistanceFormUser()
    }

    /**
     * Update distance of all rink from user
     */
    //TODO update only the one user interact with ?
    fun updateDistanceFormUser(){
        if (!myRepository.rinkList.isEmpty() && myRepository.isLocationInitialize()) {
            for (rink in myRepository.rinkList) {
                val rinkLocation = Location("RinkLocation")
                rinkLocation.longitude = rink.lng
                rinkLocation.latitude = rink.lat

                var dist = rinkLocation.distanceTo(myRepository.lastLocation)
                dist = (round(dist / 1000 * 100) / 100)

                rink.distFromUser = dist
            }
        }
    }

    fun setLiveRinkById(rinkId: Int){
        liveRinkList.value?.let {
            val newRink = it.find { it.id == rinkId }
            liveRink.value = newRink!!
        }
    }
}