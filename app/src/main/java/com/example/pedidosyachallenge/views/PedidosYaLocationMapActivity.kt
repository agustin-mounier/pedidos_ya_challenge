package com.example.pedidosyachallenge.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.py_map_layout.*

class PedidosYaLocationMapActivity: PedidosYaMapBaseActivity() {

    companion object {
        const val PositionExtra = "position_extra"
    }

    private lateinit var selectedPosition: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showMarker()
        confirm_location_button.setOnClickListener {
            val data = Intent()
            data.putExtra(PositionExtra, selectedPosition)
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    private fun showMarker() {
        point.visibility = View.VISIBLE
        marker.visibility = View.VISIBLE
        confirm_location_button.visibility = View.VISIBLE
    }

    override fun onMapReady(map: GoogleMap?) {
        super.onMapReady(map)
        map?.setOnCameraIdleListener {
            selectedPosition = map.cameraPosition.target
        }
    }
}