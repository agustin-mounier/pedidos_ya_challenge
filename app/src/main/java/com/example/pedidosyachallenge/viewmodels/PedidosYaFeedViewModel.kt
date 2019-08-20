package com.example.pedidosyachallenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pedidosyachallenge.models.Point
import com.example.pedidosyachallenge.models.Restaurant
import com.example.pedidosyachallenge.repository.PedidosYaRepository
import javax.inject.Inject

class PedidosYaFeedViewModel @Inject constructor(private val repository: PedidosYaRepository) : ViewModel() {

    private var currentPoint: Point = Point(0.0, 0.0)
    private var currentPage: Int = 0

    fun setPoint(point: Point) {
        currentPoint = point
    }

    fun resetPage() {
        currentPage = 0
    }

    fun getCurrentPage(): Int {
        return currentPage - 1
    }

    fun isLoading(): LiveData<Boolean> {
        return repository.isLoading()
    }

    fun getRestaurants(): LiveData<List<Restaurant>> {
        return repository.getRestaurants()
    }

    fun fetchRestaurants() {
        repository.fetchRestaurants(currentPoint, currentPage++)
    }
}