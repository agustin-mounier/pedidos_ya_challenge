package com.example.pedidosyachallenge.models

data class Point(val lat: Double, val long: Double) {
    override fun toString(): String {
        return "$lat,$long"
    }
}