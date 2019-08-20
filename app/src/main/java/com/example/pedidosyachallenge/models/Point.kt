package com.example.pedidosyachallenge.models

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class Point(val lat: Double, val long: Double): Serializable {
    override fun toString(): String {
        return "$lat,$long"
    }

    fun toLatLng(): LatLng {
        return LatLng(lat, long)
    }
}