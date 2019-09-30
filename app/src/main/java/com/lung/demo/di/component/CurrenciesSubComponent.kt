package com.lung.demo.di.component

import com.lung.demo.di.modules.CurrenciesModule
import com.lung.demo.di.modules.CurrenciesViewModule
import com.lung.demo.di.scope.CurrenciesScope
import com.lung.demo.presentation.CurrenciesActivity
import com.lung.demo.presentation.list.CurrencyViewHolder
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(
    modules = [
        CurrenciesModule::class,
        CurrenciesViewModule::class
    ]
)
@CurrenciesScope
interface CurrenciesSubComponent : AndroidInjector<CurrenciesActivity> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<CurrenciesActivity>

}
