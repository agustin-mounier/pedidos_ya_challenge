package com.example.pedidosyachallenge.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pedidosyachallenge.models.Point
import com.example.pedidosyachallenge.models.Restaurant
import com.example.pedidosyachallenge.repository.remote.PedidosYaServiceApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PedidosYaRepository @Inject constructor(
    private val pyService: PedidosYaServiceApi
) : PedidosYaRepositoryApi {
    private val restaurants = mutableListOf<Restaurant>()
    private val liveRestaurants = MutableLiveData<List<Restaurant>>()

    override fun isLoading(): LiveData<Boolean> {
        return pyService.isLoading()
    }

    override fun getRestaurants(): LiveData<List<Restaurant>> {
        return liveRestaurants
    }

    override fun fetchRestaurants(point: Point, page: Int) {
        pyService.getRestaurants(point, page) { response ->
            response?.let { addRestaurants(it.data) }
        }
    }

    private fun addRestaurants(newRestaurants: List<Restaurant>) {
        restaurants.addAll(newRestaurants)
        liveRestaurants.value = restaurants
    }


}