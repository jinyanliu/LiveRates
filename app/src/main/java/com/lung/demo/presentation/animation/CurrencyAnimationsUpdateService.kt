package com.lung.demo.presentation.animation

interface CurrencyAnimationsUpdateService {
    val isAnimationsEnd: Boolean
    fun onIsAnimationsEnd(isAnimationsEnd: Boolean)
}