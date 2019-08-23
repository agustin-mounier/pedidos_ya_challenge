package com.example.pedidosyachallenge

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import org.mockito.Mockito.mock

class FakeApplication: Application(), HasActivityInjector {
    override fun activityInjector(): AndroidInjector<Activity> {
        return mock(AndroidInjector::class.java) as AndroidInjector<Activity>
    }
}