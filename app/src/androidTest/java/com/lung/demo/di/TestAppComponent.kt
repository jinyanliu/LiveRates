package com.lung.demo.di

import com.lung.demo.TestApp
import com.lung.demo.data.remote.CurrenciesApiService
import com.lung.demo.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TestAppModule::class,
        TestNetworkModule::class,
        DataModule::class,
        DomainModule::class,
        CurrenciesInjectModule::class,
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface TestAppComponent : AndroidInjector<TestApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance testApp: TestApp): TestAppComponent
    }

    fun currenciesApiService(): CurrenciesApiService

}