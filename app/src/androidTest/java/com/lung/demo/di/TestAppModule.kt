package com.lung.demo.di

import android.content.Context
import com.lung.demo.TestApp
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface TestAppModule {

    @Binds
    @Singleton
    fun bindContext(impl: TestApp): Context

}

