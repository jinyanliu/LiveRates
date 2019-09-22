package com.lung.demo.di.modules

import android.content.Context
import com.lung.demo.App
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface AppModule {

    @Binds
    @Singleton
    fun bindContext(impl: App): Context


}

