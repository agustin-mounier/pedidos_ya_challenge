package com.example.pedidosyachallenge.services

import com.example.pedidosyachallenge.models.AccessTokenResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PedidosYaAuthService {

    object Paths {
        const val Tokens = "tokens"
    }

    object QueryParams {
        const val ClientId = "clientId"
        const val ClientSecret = "clientSecret"
    }

    @GET(Paths.Tokens)
    fun getAccessToken(
        @Query(QueryParams.ClientId, encoded = true) clientId: String,
        @Query(QueryParams.ClientSecret, encoded = true) clientSecret: String
    ): Call<AccessTokenResponse>
}