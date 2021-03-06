package com.jbm.maglace.adapter

import android.view.View
import com.jbm.maglace.model.Rink
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow

class RinkInfoWindow(view: View, mapView: MapView?, rink: Rink) : InfoWindow(view, mapView) {

    @Override
    override fun onOpen(item: Any?) {
        //closeAllInfoWindowsOn(mMapView)
    }

    @Override
    override fun onClose() {
    }
}