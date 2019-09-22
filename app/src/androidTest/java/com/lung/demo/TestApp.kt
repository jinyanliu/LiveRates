package com.lung.demo

import androidx.test.core.app.ApplicationProvider
import com.lung.demo.di.DaggerTestAppComponent
import com.lung.demo.di.TestAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class TestApp : DaggerApplication() {

    private val appComponent by lazy {
        DaggerTestAppComponent
            .factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)
    }

    companion object {
        fun appComponent(): TestAppComponent {
            return (ApplicationProvider.getApplicationContext<TestApp>().applicationContext as TestApp).appComponent
        }
    }

    override fun applicationInjector():
            AndroidInjector<out DaggerApplication> = appComponent
}