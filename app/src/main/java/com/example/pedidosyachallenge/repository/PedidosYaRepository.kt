package com.example.pedidosyachallenge.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pedidosyachallenge.PedidosYaApplication
import com.example.pedidosyachallenge.models.Point
import com.example.pedidosyachallenge.models.Restaurant
import com.example.pedidosyachallenge.repository.local.PedidosYaDao
import com.example.pedidosyachallenge.repository.remote.ErrorType
import com.example.pedidosyachallenge.repository.remote.PedidosYaServiceApi
import com.example.pedidosyachallenge.utils.NetworkUtils
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Restaurants repository that acts as a single source of truth for all the app data.
 * Depending on the network connectivity it fetches the data locally or remotely.
 */
@Singleton
class PedidosYaRepository @Inject constructor(
    private val app: PedidosYaApplication,
    private val pyService: PedidosYaServiceApi,
    private val pyDao: PedidosYaDao
) : PedidosYaRepositoryApi {
    private val restaurants = mutableListOf<Restaurant>()
    private val liveRestaurants = MutableLiveData<List<Restaurant>>()

    override fun isLoading(): LiveData<Boolean> {
        return pyService.isLoading()
    }

    override fun getErrorType(): LiveData<ErrorType> {
        return pyService.getErrorType()
    }

    override fun getRestaurants(): LiveData<List<Restaurant>> {
        return liveRestaurants
    }

    override fun fetchRestaurants(point: Point, page: Int) {
        if (NetworkUtils.isNetworkAvailable(app)) {
            pyService.getRestaurants(point, page) { response ->
                response?.let {
                    addRestaurants(it.data)
                    pyDao.persistRestaurants(it.data)
                }
            }
        } else if (restaurants.isEmpty()) { // If there are no restaurants, fetch restaurants locally
            val localRestaurants = pyDao.retrieveRestaurants()
            addRestaurants(localRestaurants)
        }
    }

    override fun clearRestaurants() {
        restaurants.clear()
    }

    private fun addRestaurants(newRestaurants: List<Restaurant>) {
        restaurants.addAll(newRestaurants)
        liveRestaurants.value = restaurants
    }
}