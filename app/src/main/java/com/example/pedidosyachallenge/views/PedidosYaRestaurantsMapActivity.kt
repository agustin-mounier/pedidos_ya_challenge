package com.example.pedidosyachallenge.views

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.pedidosyachallenge.models.Point
import com.example.pedidosyachallenge.viewmodels.PedidosYaMapViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject


class PedidosYaRestaurantsMapActivity : PedidosYaMapBaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PedidosYaMapViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[PedidosYaMapViewModel::class.java]
    }

    override fun onMapReady(map: GoogleMap?) {
        super.onMapReady(map)
        val restaurants = viewModel.getRestaurants().value
        restaurants?.forEach {
            val coordinates = it.coordinates.split(",")
            val location = LatLng(coordinates[0].toDouble(), coordinates[1].toDouble())
            val marker = MarkerOptions().position(location).title(it.name)
            map?.addMarker(marker)
        }
    }
}