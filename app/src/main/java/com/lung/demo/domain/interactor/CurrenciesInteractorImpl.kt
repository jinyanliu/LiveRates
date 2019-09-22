package com.lung.demo.domain.interactor

import com.lung.demo.domain.repository.CurrenciesRepository
import com.lung.demo.domain.scheduler.SchedulersProvider
import com.lung.demo.domain.status.CurrenciesStatusService
import com.lung.demo.domain.mapper.CurrencyMapper
import com.lung.demo.domain.model.Currency
import com.lung.demo.domain.repository.CurrenciesLocalResourcesService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.math.BigDecimal
import javax.inject.Inject

class CurrenciesInteractorImpl @Inject constructor(
    private val currenciesRepository: CurrenciesRepository,
    private val currenciesStatusService: CurrenciesStatusService,
    private val currencyMapper: CurrencyMapper,
    private val schedulers: SchedulersProvider
) : CurrenciesInteractor {

    override val observeUpdatedAmountCurrencies: Flowable<List<Currency>> by lazy {
        currenciesStatusService.observeUpdatedAmountCurrencies
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.ui)
    }

    override val observeCurrencies: Flowable<List<Currency>> by lazy {
        currenciesStatusService.observeCurrencies
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.ui)
    }

    override val observePolling: Flowable<Unit> by lazy {
        currenciesStatusService.observePerSecond
            .flatMapSingle {
                val (base, amount) = currenciesStatusService.baseAndAmount
                currenciesRepository.getActualCurrencies(base, amount)
                    .map { convertRate(base, amount, it) }
            }.map {
                if (currenciesStatusService.isBaseChanged) {
                    currenciesStatusService.onCurrenciesUpdated(it)
                } else {
                    currenciesStatusService.onCurrenciesAmountUpdated(it)
                }
                currenciesStatusService.onIsBaseChanged(false)
            }
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.ui)
    }

    override fun onBaseAndAmountUpdated(base: String, amount: BigDecimal) = Completable.fromAction {
        currenciesStatusService.onBaseAndAmountUpdated(base, amount)
        currenciesStatusService.onIsBaseChanged(true)
    }.subscribeOn(schedulers.io)
        .observeOn(schedulers.ui)

    override fun onAmountUpdated(amount: BigDecimal): Completable = Completable.fromAction {
        currenciesStatusService.onAmountUpdated(amount)
        val base = currenciesStatusService.base
        val list = convertRate(base, amount, currenciesStatusService.updatedAmountCurrencies)
        currenciesStatusService.onCurrenciesAmountUpdated(list)
    }.subscribeOn(schedulers.io)
        .observeOn(schedulers.ui)

    override fun clear() {
        currenciesStatusService.clear()
    }

    private fun convertRate(
        base: String,
        amount: BigDecimal,
        currencies: List<Currency>
    ): List<Currency> {
        return currencies.map {
            if (base == it.name) {
                it
            } else {
                currencyMapper.map(
                    it.name,
                    it.description,
                    it.img,
                    it.rate,
                    currencyMapper.mapAmount(it.rate, amount)
                )
            }
        }
    }


}
