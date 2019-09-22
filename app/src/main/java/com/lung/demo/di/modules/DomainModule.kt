package com.lung.demo.di.modules

import com.lung.demo.domain.scheduler.SchedulersProvider
import com.lung.demo.presentation.schedular.SchedulersProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface DomainModule {

    @Binds
    @Reusable
    fun bindSchedulers(impl: SchedulersProviderImpl): SchedulersProvider
}