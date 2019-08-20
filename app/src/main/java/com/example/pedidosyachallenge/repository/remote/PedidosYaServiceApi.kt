package com.example.pedidosyachallenge.repository.remote

import androidx.lifecycle.LiveData
import com.example.pedidosyachallenge.models.Point
import com.example.pedidosyachallenge.models.RestaurantsResponse

interface PedidosYaServiceApi {

    fun isLoading(): LiveData<Boolean>

    fun getRestaurants(point: Point, page: Int, onSuccessFun: (RestaurantsResponse?) -> Unit)
}