package com.lung.demo.di.modules

import com.lung.demo.di.scope.CurrenciesScope
import com.lung.demo.domain.interactor.CurrenciesInteractorImpl
import com.lung.demo.domain.interactor.CurrenciesInteractor
import com.lung.demo.presentation.model.CurrenciesVM
import com.lung.demo.presentation.list.CurrenciesAdapter
import com.lung.demo.presentation.animation.CurrencyAnimationsUpdateService
import com.lung.demo.presentation.animation.CurrencyAnimationsUpdateServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CurrenciesModule {

    @Binds
    @CurrenciesScope
    abstract fun bindIntractor(impl: CurrenciesInteractorImpl): CurrenciesInteractor

    @Binds
    @CurrenciesScope
    abstract fun bindCurrencyAnimationsUpdateService(impl: CurrencyAnimationsUpdateServiceImpl): CurrencyAnimationsUpdateService

}

@Module
class CurrenciesViewModule {

    @Provides
    @CurrenciesScope
    fun provideCurrenciesAdapter(vm: CurrenciesVM) = CurrenciesAdapter(vm)


}
