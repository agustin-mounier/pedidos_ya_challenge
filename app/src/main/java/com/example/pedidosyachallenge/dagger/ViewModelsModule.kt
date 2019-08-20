package com.example.pedidosyachallenge.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pedidosyachallenge.viewmodels.LoginViewModel
import com.example.pedidosyachallenge.viewmodels.PedidosYaFeedViewModel
import com.example.pedidosyachallenge.viewmodels.PedidosYaMapViewModel
import com.example.pedidosyachallenge.viewmodels.ViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelsModule {

    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    @MapKey
    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PedidosYaFeedViewModel::class)
    internal abstract fun pedidosYaFeedViewModel(viewModel: PedidosYaFeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun loginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PedidosYaMapViewModel::class)
    internal abstract fun mapViewModel(viewModel: PedidosYaMapViewModel): ViewModel
}