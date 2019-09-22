package com.lung.demo.domain.status

import com.lung.demo.domain.model.Currency
import io.reactivex.Flowable
import java.math.BigDecimal

interface CurrenciesStatusService {
    val base: String
    val amount: BigDecimal
    val baseAndAmount: Pair<String, BigDecimal>
    val isBaseChanged: Boolean
    val updatedAmountCurrencies: List<Currency>

    val observeUpdatedAmountCurrencies: Flowable<List<Currency>>
    val observeCurrencies: Flowable<List<Currency>>
    val observePerSecond: Flowable<Long>

    fun onAmountUpdated(amount: BigDecimal)
    fun onBaseAndAmountUpdated(base: String, amount: BigDecimal)
    fun onIsBaseChanged(isBaseChanged: Boolean)
    fun onCurrenciesUpdated(currencies: List<Currency>)
    fun onCurrenciesAmountUpdated(currencies: List<Currency>)
    fun clear()

}