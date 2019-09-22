package com.lung.demo

import com.lung.demo.domain.scheduler.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TestSchedulersProviderImpl @Inject constructor() : SchedulersProvider {

    override val io: Scheduler
        get() = Schedulers.trampoline()

    override val computation: Scheduler
        get() = Schedulers.trampoline()

    override val ui: Scheduler
        get() = Schedulers.trampoline()

}