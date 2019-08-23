package com.example.pedidosyachallenge.dagger

import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration


@Module
class RealmModule {
    @Provides
    fun providesRealm(): Realm = Realm.getDefaultInstance()
}