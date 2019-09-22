package com.lung.demo.utils

object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = SimpleCountingIdlingResource(RESOURCE)

    fun increment() {
        if(countingIdlingResource.isIdleNow){
            countingIdlingResource.increment()
        }
    }

    fun decrement() {
        if(!countingIdlingResource.isIdleNow){
            countingIdlingResource.decrement()
        }
    }

}
