package com.example.pedidosyachallenge.repository.local

import com.example.pedidosyachallenge.models.Restaurant
import io.realm.Realm
import javax.inject.Inject

class PedidosYaDaoImpl @Inject constructor(private val realm: Realm): PedidosYaDao {

    override fun retrieveRestaurants(): List<Restaurant> {
        val model = realm.where(Restaurant::class.java).findAll()
        return realm.copyFromRealm(model)
    }

    override fun persistRestaurants(restaurants: List<Restaurant>) {
        realm.executeTransactionAsync { it.insertOrUpdate(restaurants) }
    }
}