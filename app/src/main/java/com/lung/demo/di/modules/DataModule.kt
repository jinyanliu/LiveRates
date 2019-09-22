package com.lung.demo.di.modules

import com.lung.demo.domain.repository.CurrenciesRepository
import com.lung.demo.data.CurrenciesRepositoryImpl
import com.lung.demo.data.local.CurrenciesStatusServiceImpl
import com.lung.demo.data.local.CurrenciesLocalResourcesServiceImpl
import com.lung.demo.domain.repository.CurrenciesLocalResourcesService
import com.lung.demo.domain.status.CurrenciesStatusService
import dagger.Binds
import dagger.Module
import dagger.Reusable
import javax.inject.Singleton

@Module
interface DataModule {

    @Binds
    @Singleton
    fun bindCurrenciesRepository(impl: CurrenciesRepositoryImpl): CurrenciesRepository

    @Binds
    @Singleton
    fun bindCurrenciesConfigService(impl: CurrenciesStatusServiceImpl): CurrenciesStatusService

    @Binds
    @Reusable
    fun bindCurrenciesLocalResourcesService(impl: CurrenciesLocalResourcesServiceImpl): CurrenciesLocalResourcesService
}