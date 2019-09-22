# LiveRates


### Project Overview

This project demonstrates MVVM with Clean Architecture in Kotlin. It updates rates every second.When changing the amount , it simultaneously updates the corresponding value for other currencies.

### Tech-stack

<img src="extras/images/demo.gif" width="336" align="right" hspace="20">

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) 
    * [RxJava](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid)
    * [Dagger 2](http://google.github.io/dagger/) - dependency injection
    * [Retrofit](https://square.github.io/retrofit/) - networking
    * [Jetpack](https://developer.android.com/jetpack)
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - notify views about data change
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform action when lifecycle state changes
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way
    * [Stetho](http://facebook.github.io/stetho/) - application debugging tool
    * [Glide](https://github.com/bumptech/glide) - image loading and caching
* Architecture
    * [Clean Architecture](https://proandroiddev.com/kotlin-clean-architecture-1ad42fcd97fa) 
    * [MVVM](https://www.raywenderlich.com/636803-mvvm-and-databinding-android-design-patterns)
* Tests
    * [Unit Tests](https://en.wikipedia.org/wiki/Unit_testing) ([JUnit](https://junit.org/junit4/))
    * [Mockito](https://github.com/mockito/mockito) + [Mockito-Kotlin](https://github.com/nhaarman/mockito-kotlin)
    * [Espresso](https://developer.android.com/training/testing/espresso)
    * [Robolectric](http://robolectric.org/)

### Tests

To run **unit** tests on your machine:

``` 
./gradlew test
``` 

To run **ui** tests on connected devices:

``` 
./gradlew connectedAndroidTest
``` 