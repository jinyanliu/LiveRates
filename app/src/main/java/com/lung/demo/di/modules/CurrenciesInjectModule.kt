package com.lung.demo.di.modules

import com.lung.demo.di.component.CurrenciesSubComponent
import com.lung.demo.presentation.CurrenciesActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [CurrenciesSubComponent::class])
interface CurrenciesInjectModule {

    @Binds
    @IntoMap
    @ClassKey(CurrenciesActivity::class)
    fun bindCurrenciesActivityInjectorFactory(builder: CurrenciesSubComponent.Factory): AndroidInjector.Factory<*>

}