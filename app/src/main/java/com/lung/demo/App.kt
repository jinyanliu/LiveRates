package com.lung.demo

import com.facebook.stetho.Stetho
import com.lung.demo.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {

    private val injector by lazy { DaggerAppComponent.factory().create(this) }

    override fun onCreate() {
        super.onCreate()

        setupDebugUtils()
    }

    private fun setupDebugUtils() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
    }

    override fun applicationInjector():
            AndroidInjector<out DaggerApplication> = injector
}