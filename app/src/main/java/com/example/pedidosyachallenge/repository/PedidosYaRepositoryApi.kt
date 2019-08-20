package com.example.pedidosyachallenge.repository

import androidx.lifecycle.LiveData
import com.example.pedidosyachallenge.models.Point
import com.example.pedidosyachallenge.models.Restaurant

interface PedidosYaRepositoryApi {


    fun isLoading(): LiveData<Boolean>

    fun getRestaurants(): LiveData<List<Restaurant>>

    fun fetchRestaurants(point: Point, page: Int)
}