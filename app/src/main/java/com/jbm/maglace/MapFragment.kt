package com.jbm.maglace

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.jbm.maglace.model.Rink
import com.jbm.maglace.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@AndroidEntryPoint
class MapFragment : Fragment() {
    val TAG: String =  "tag.jbm." + this::class.java.simpleName

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var mapView: MapView

    @Override
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_map, container, false)

        setUpMap(root)

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

    fun addRinksOverlay(rinks: List<Rink>) {
        for (rink in rinks) {
            val marker = Marker(mapView)
            marker.icon = resources.getDrawable(rink.getTypeMarkerDrawable(), resources.newTheme())
            marker.position = GeoPoint(rink.lat, rink.lng)

            marker.title = rink.name
            marker.image = resources.getDrawable(rink.getConditionDrawable(), resources.newTheme())

            mapView.getOverlays().add(marker)
        }

        mapView.invalidate()
    }

    fun setUpMap(view: View) {
        mapView = view.findViewById(R.id.map)

        val mapController = mapView.controller
        mapController.animateTo(GeoPoint(45.52219950438451, -73.62182868026389))
        mapController.setZoom(14.5)

        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID)
        mapView.setTileSource(TileSourceFactory.MAPNIK)

        mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)
        mapView.setMultiTouchControls(true)

        var locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), mapView)
        locationOverlay.enableMyLocation()
        locationOverlay.enableFollowLocation()
        mapView.overlays.add(locationOverlay)
    }

}