package com.example.pedidosyachallenge.dagger

import com.example.pedidosyachallenge.PedidosYaApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ViewModelsModule::class,
    ActivitiesModule::class,
    Providers::class,
    AndroidInjectionModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: PedidosYaApplication): Builder

        fun build(): AppComponent
    }

    fun inject(app: PedidosYaApplication)
}