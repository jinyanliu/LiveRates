package com.lung.demo.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesApiService {
    @GET("latest")
    fun getCurrencies(@Query("base") base: String): Single<CurrenciesResponse>
}