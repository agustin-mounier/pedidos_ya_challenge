package com.example.pedidosyachallenge.repository

import androidx.lifecycle.LiveData
import com.example.pedidosyachallenge.models.Point
import com.example.pedidosyachallenge.models.Restaurant
import com.example.pedidosyachallenge.repository.remote.ErrorType

interface PedidosYaRepositoryApi {

    fun isLoading(): LiveData<Boolean>

    fun getErrorType(): LiveData<ErrorType>

    fun getRestaurants(): LiveData<List<Restaurant>>

    fun fetchRestaurants(point: Point, page: Int)

    fun clearRestaurants()
}