package com.lung.demo.presentation.schedular

import com.lung.demo.domain.scheduler.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import io.reactivex.android.schedulers.AndroidSchedulers

class SchedulersProviderImpl @Inject constructor():
    SchedulersProvider {

    override val io: Scheduler
        get() = Schedulers.io()

    override val computation: Scheduler
        get() = Schedulers.computation()

    override val ui: Scheduler
        get() = AndroidSchedulers.mainThread()

}