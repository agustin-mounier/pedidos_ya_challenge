package com.example.pedidosyachallenge.dagger

import com.example.pedidosyachallenge.repository.PedidosYaRepository
import com.example.pedidosyachallenge.repository.PedidosYaRepositoryApi
import com.example.pedidosyachallenge.repository.remote.PedidosYaServiceApi
import com.example.pedidosyachallenge.repository.remote.PedidosYaServiceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun bindRepository(repository: PedidosYaRepository): PedidosYaRepositoryApi

    @Binds
    abstract fun bindService(service: PedidosYaServiceImpl): PedidosYaServiceApi
}