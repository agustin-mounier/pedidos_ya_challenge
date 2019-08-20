package com.example.pedidosyachallenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pedidosyachallenge.models.Restaurant
import com.example.pedidosyachallenge.repository.PedidosYaRepositoryApi
import javax.inject.Inject

class PedidosYaMapViewModel @Inject constructor(val repository: PedidosYaRepositoryApi): ViewModel() {

    fun getRestaurants(): LiveData<List<Restaurant>> {
        return repository.getRestaurants()
    }
}