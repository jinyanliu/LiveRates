package com.lung.demo.presentation.animation

import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class CurrencyAnimationsUpdateServiceImpl @Inject constructor() :
    CurrencyAnimationsUpdateService {

    private val isAnimationsEndSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(true)

    override fun onIsAnimationsEnd(isAnimationsEnd: Boolean) {
        isAnimationsEndSubject.onNext(isAnimationsEnd)
    }

    override val isAnimationsEnd: Boolean
        get() = isAnimationsEndSubject.value!!

}