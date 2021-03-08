package com.jbm.maglace.ui

import android.content.Context
import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jbm.maglace.adapter.RinkInfoWindow
import com.jbm.maglace.databinding.ListItemRinkBinding
import com.jbm.maglace.model.MyRepository
import com.jbm.maglace.model.Rink
import com.jbm.maglace.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.infowindow.InfoWindow
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class MainViewModel @Inject constructor(
    val myRepository: MyRepository): ViewModel()
{
    val TAG: String =  "tag.jbm." + this::class.java.simpleName

    var liveRinkList = MutableLiveData<MutableList<Rink>>()
    var liveRink = MutableLiveData<Rink>()
    var liveFilter = MutableLiveData(Constants().FILTER_ALL)

    init {
        updateRinkListData()
    }

    fun updateRinkListData() {
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