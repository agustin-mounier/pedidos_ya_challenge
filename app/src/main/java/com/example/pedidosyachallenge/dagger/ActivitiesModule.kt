package com.example.pedidosyachallenge.dagger

import com.example.pedidosyachallenge.views.LoginActivity
import com.example.pedidosyachallenge.views.PedidosYaFeedActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    internal abstract fun contributesPYFeedActivity(): PedidosYaFeedActivity

    @ContributesAndroidInjector
    internal abstract fun contributesPYLogInActivity(): LoginActivity
}