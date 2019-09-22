package com.lung.demo.data

import com.lung.demo.data.remote.CurrenciesApiService
import com.lung.demo.data.remote.CurrenciesResponse
import com.lung.demo.domain.mapper.CurrencyMapper
import com.lung.demo.domain.repository.CurrenciesLocalResourcesService
import com.lung.demo.domain.repository.CurrenciesRepository
import com.lung.demo.domain.model.Currency
import io.reactivex.Single
import java.math.BigDecimal
import javax.inject.Inject

class CurrenciesRepositoryImpl @Inject constructor(
    private val currenciesApiService: CurrenciesApiService,
    private val currenciesLocalResourcesService: CurrenciesLocalResourcesService,
    private val currencyMapper: CurrencyMapper
) : CurrenciesRepository {

    override fun getActualCurrencies(base: String, amount: BigDecimal): Single<List<Currency>> {
        return currenciesApiService.getCurrencies(base)
            .map(this::mapCurrency)
            .map {
                addBase(base, amount, it)
            }
    }

    private fun mapCurrency(it: CurrenciesResponse): List<Currency> {
        return it.rates.map { (name, rate) ->
            val (description, img) = currenciesLocalResourcesService.getDescriptionAndImage(
                name
            )
            currencyMapper.map(name, description, img, BigDecimal(rate), BigDecimal.ONE)
        }
    }

    private fun addBase(
        base: String,
        amount: BigDecimal,
        currencies: List<Currency>
    ): List<Currency> {
        val (description, img) = currenciesLocalResourcesService.getDescriptionAndImage(base)
        val baseCurrency = currencyMapper.map(base, description, img, BigDecimal(1), amount)
        return listOf(baseCurrency) + currencies
    }


}