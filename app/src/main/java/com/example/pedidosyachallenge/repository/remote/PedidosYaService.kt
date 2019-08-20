package com.example.pedidosyachallenge.repository.remote

import com.example.pedidosyachallenge.models.RestaurantsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PedidosYaService {

    companion object {
        const val BaseUrl = "http://stg-api.pedidosya.com/public/v1/"
        const val BaseImgUrl = "https://img.pystatic.com/"
        const val PageSize = 20
    }

    object Paths {
        const val Search = "search"
        const val Restaurants = "restaurants"
    }

    object QueryParams {
        const val Point = "point"
        const val Country = "country"
        const val Offset = "offset"
        const val Max = "max"
    }

    object Headers {
        const val Authorization = "Authorization"
    }

    @GET(Paths.Search + "/" + Paths.Restaurants)
    fun getRestaurants(
        @Header(Headers.Authorization) token: String,
        @Query(QueryParams.Point, encoded = true) point: String,
        @Query(QueryParams.Country) country: Int,
        @Query(QueryParams.Offset) offset: Int,
        @Query(QueryParams.Max) max: Int
    ): Call<RestaurantsResponse>
}