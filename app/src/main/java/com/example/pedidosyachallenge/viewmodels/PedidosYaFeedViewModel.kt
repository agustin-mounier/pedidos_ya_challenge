package com.example.pedidosyachallenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pedidosyachallenge.models.Point
import com.example.pedidosyachallenge.models.Restaurant
import com.example.pedidosyachallenge.repository.PedidosYaRepositoryApi
import com.example.pedidosyachallenge.repository.remote.ErrorType
import javax.inject.Inject

class PedidosYaFeedViewModel @Inject constructor(private val repository: PedidosYaRepositoryApi) : ViewModel() {

    private lateinit var currentPoint: Point
    private var currentPage: Int = 0

    fun setPoint(point: Point) {
        currentPoint = point
    }

    fun getPoint(): Point {
        return currentPoint
    }

    fun getCurrentPage(): Int {
        return if (currentPage == 0) 0 else currentPage - 1
    }

    fun isLoading(): LiveData<Boolean> {
        return repository.isLoading()
    }

    fun getErrorType(): LiveData<ErrorType> {
        return repository.getErrorType()
    }

    fun getRestaurants(): LiveData<List<Restaurant>> {
        return repository.getRestaurants()
    }

    fun fetchRestaurants() {
        repository.fetchRestaurants(currentPoint, currentPage++)
    }

    fun clearRestaurants() {
        currentPage = 0
        repository.clearRestaurants()
    }

}