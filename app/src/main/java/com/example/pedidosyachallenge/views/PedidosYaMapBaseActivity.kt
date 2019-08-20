package com.example.pedidosyachallenge.views

import android.os.Bundle
import android.os.PersistableBundle
import com.example.pedidosyachallenge.R
import com.example.pedidosyachallenge.models.Point
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.DaggerAppCompatActivity

abstract class PedidosYaMapBaseActivity:  DaggerAppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val SelectedLocationKey = "selected_location_key"
    }

    private lateinit var selectedLocation: Point

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.py_map_layout)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        selectedLocation = if(savedInstanceState != null) {
            savedInstanceState[SelectedLocationKey] as Point
        } else {
            intent?.extras?.get(PedidosYaFeedActivity.SelectedLocation) as Point
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        val deviceCoords = LatLng(selectedLocation.lat, selectedLocation.long)
        val latLng = CameraUpdateFactory.newLatLngZoom(deviceCoords, 15.0f)
        map?.animateCamera(latLng)
        val userMarker = MarkerOptions().position(selectedLocation.toLatLng())
            .title("You")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        map?.addMarker(userMarker)
    }


    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putSerializable(SelectedLocationKey, selectedLocation)
    }
}