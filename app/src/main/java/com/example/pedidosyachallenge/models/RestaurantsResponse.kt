package com.example.pedidosyachallenge.models

data class RestaurantsResponse(
    val total: Int,
    val sort: String,
    val max: Int,
    val count: Int,
    val data: List<Restaurant>
)