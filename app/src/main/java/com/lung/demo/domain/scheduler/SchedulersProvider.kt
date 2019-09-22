package com.lung.demo.domain.scheduler

import io.reactivex.Scheduler

interface SchedulersProvider {
    val io: Scheduler
    val computation: Scheduler
    val ui: Scheduler
}