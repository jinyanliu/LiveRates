package com.lung.demo.domain.repository

import com.lung.demo.domain.model.Currency
import io.reactivex.Single
import java.math.BigDecimal

interface CurrenciesRepository {
    fun getActualCurrencies(base: String, amount: BigDecimal): Single<List<Currency>>
}