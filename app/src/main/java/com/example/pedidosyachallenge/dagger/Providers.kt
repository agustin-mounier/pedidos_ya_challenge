package com.example.pedidosyachallenge.dagger

import com.example.pedidosyachallenge.repository.remote.PedidosYaService
import com.example.pedidosyachallenge.services.PedidosYaAuthService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class Providers {

    @Provides
    @Singleton
    fun bindPedidosYaService(): PedidosYaService {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PedidosYaService.BaseUrl)
            .build()
            .create(PedidosYaService::class.java)
    }

    @Provides
    @Singleton
    fun bindAuthService(): PedidosYaAuthService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PedidosYaService.BaseUrl)
            .build()
            .create(PedidosYaAuthService::class.java)
    }
}