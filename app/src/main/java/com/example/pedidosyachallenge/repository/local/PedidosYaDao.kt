package com.example.pedidosyachallenge.repository.local

import com.example.pedidosyachallenge.models.Restaurant

interface PedidosYaDao {

    fun persistRestaurants(restaurants: List<Restaurant>)

    fun retrieveRestaurants(): List<Restaurant>
}