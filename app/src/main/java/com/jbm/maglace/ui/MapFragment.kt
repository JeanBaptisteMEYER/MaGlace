package com.jbm.maglace.ui

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jbm.maglace.BuildConfig
import com.jbm.maglace.R
import com.jbm.maglace.adapter.RinkInfoWindow
import com.jbm.maglace.databinding.ListItemRinkBinding
import com.jbm.maglace.model.Rink
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@AndroidEntryPoint
class MapFragment : Fragment() {
    val TAG: String =  "tag.jbm." + this::class.java.simpleName

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var mapView: MapView
    private lateinit var currentMarker: Marker

    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_map, container, false)

        initUi(root)

        return root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.liveRinkList.observe(viewLifecycleOwner, {
            addRinksOverlay(it)
        })
    }

    @Override
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    @Override
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    /**
     * add all rinks marker to the map
     */
    fun addRinksOverlay(rinks: List<Rink>) {

        var markers = arrayListOf<Marker>()
        val parent = mapView.parent as ViewGroup
        val mapContext: Context = mapView.context

        for (rink in rinks) {
            // new mark with position and icon
            val marker = Marker(mapView)
            marker.icon = resources.getDrawable(rink.getMarkerDrawable(), resources.newTheme())
            marker.position = GeoPoint(rink.lat, rink.lng)

            // binding view list_item_rink to be use in marker's infoWindows
            val binding =
                ListItemRinkBinding.inflate(LayoutInflater.from(mapContext), parent, false)
            binding.rink = rink
            binding.setClickListener { view -> marker.closeInfoWindow() }

            // Create info widows from binding.root
            marker.infoWindow = RinkInfoWindow(binding.root, mapView, rink)

            // show/hide marker on marker click
            marker.setOnMarkerClickListener { marker, mapView ->
                marker?.let {
                    if (it.infoWindow.isOpen) {
                        it.infoWindow.close()
                    } else {
                        InfoWindow.closeAllInfoWindowsOn(mapView)
                        it.infoWindow.open(it, it.position, 0, -90)
                        currentMarker = marker
                    }
                }
                true
            }

            markers.add(marker)
        }

        // add overlayto map
        mapView.getOverlays().addAll(markers)
        mapView.invalidate()
    }

    fun initUi(view: View) {
        //setup Map
        mapView = view.findViewById(R.id.map)

        val mapController = mapView.controller
        // default position and zoom
        mapController.setCenter(GeoPoint(45.52219950438451, -73.62182868026389))
        mapController.setZoom(12.0)

        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID)
        mapView.setTileSource(TileSourceFactory.MAPNIK)

        mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)
        mapView.setMultiTouchControls(true)

        // enable my location overlay
        var locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), mapView)
        locationOverlay.enableMyLocation()
        locationOverlay.enableFollowLocation()

        // when getting the first location fix from osm, updateViewModel with location
        locationOverlay.runOnFirstFix {
            mainViewModel.setFirstLocation(locationOverlay.lastFix)
            centerZoomOnUser(locationOverlay.lastFix)
        }
        mapView.overlays.add(locationOverlay)

        //set up button
        view.findViewById<FloatingActionButton>(R.id.centerMapButton).setOnClickListener {
            centerZoomOnUser(mainViewModel.myRepository.lastLocation)
        }
    }

    fun centerZoomOnUser(location: Location) {
        MainScope().launch {
            mapView.controller.animateTo(GeoPoint(location.latitude, location.longitude), 15.0, 1000)
        }
    }

}
