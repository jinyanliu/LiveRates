package com.lung.demo.domain.interactor

import com.lung.demo.domain.model.Currency
import io.reactivex.Completable
import io.reactivex.Flowable
import java.math.BigDecimal

interface CurrenciesInteractor : Interactor {
    val observeCurrencies: Flowable<List<Currency>>
    val observeUpdatedAmountCurrencies: Flowable<List<Currency>>
    val observePolling: Flowable<Unit>
    fun onBaseAndAmountUpdated(base: String, amount: BigDecimal): Completable
    fun onAmountUpdated(amount: BigDecimal): Completable
}