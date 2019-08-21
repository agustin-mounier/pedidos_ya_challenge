package com.example.pedidosyachallenge.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.pedidosyachallenge.R
import com.example.pedidosyachallenge.models.Point
import com.example.pedidosyachallenge.viewmodels.PedidosYaMapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.py_map_layout.*
import javax.inject.Inject

class PedidosYaMapActivity : DaggerAppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val SelectedLocationKey = "selected_location_key"
        const val PositionExtra = "position_extra"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PedidosYaMapViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[PedidosYaMapViewModel::class.java]
    }

    private val markers = mutableListOf<Marker?>()
    private lateinit var selectedLocation: Point
    private lateinit var selectedPosition: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.py_map_layout)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        selectedLocation = if (savedInstanceState != null) {
            savedInstanceState[SelectedLocationKey] as Point
        } else {
            intent?.extras?.get(PedidosYaFeedActivity.SelectedLocation) as Point
        }

        initView()
    }

    override fun onMapReady(map: GoogleMap?) {
        setDeviceMarker(map)
        setRestaurantMarkers(map)
        map?.setOnCameraIdleListener {
            selectedPosition = map.cameraPosition.target
        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putSerializable(SelectedLocationKey, selectedLocation)
    }

    private fun initView() {
        confirm_location_button.setOnClickListener {
            val data = Intent()
            data.putExtra(PositionExtra, selectedPosition)
            setResult(Activity.RESULT_OK, data)
            finish()
        }

        change_location.setOnClickListener {
            markers.forEach { it?.remove() }
            showPositionMarker()
        }
    }


    private fun showPositionMarker() {
        point.visibility = View.VISIBLE
        marker.visibility = View.VISIBLE
        confirm_location_button.visibility = View.VISIBLE
    }

    private fun setDeviceMarker(map: GoogleMap?) {
        val deviceCoords = LatLng(selectedLocation.lat, selectedLocation.long)
        val latLng = CameraUpdateFactory.newLatLngZoom(deviceCoords, 15.0f)
        map?.animateCamera(latLng)
        val userMarker = MarkerOptions().position(selectedLocation.toLatLng())
            .title("You")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        map?.addMarker(userMarker)
    }

    private fun setRestaurantMarkers(map: GoogleMap?) {
        val restaurants = viewModel.getRestaurants().value
        restaurants?.forEach {
            val coordinates = it.coordinates.split(",")
            val location = LatLng(coordinates[0].toDouble(), coordinates[1].toDouble())
            val marker = MarkerOptions().position(location).title(it.name)
            markers.add(map?.addMarker(marker))
        }
    }
}