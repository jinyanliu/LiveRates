package com.lung.demo.data.local

import com.lung.demo.domain.status.CurrenciesStatusService
import com.lung.demo.domain.model.Currency
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrenciesStatusServiceImpl @Inject constructor() :
    CurrenciesStatusService {

    private val baseSubject: BehaviorSubject<String> = BehaviorSubject.createDefault(DEFAULT_BASE)

    private val amountSubject: BehaviorSubject<BigDecimal> =
        BehaviorSubject.createDefault(DEFAULT_AMOUNT)

    private val updatedAmountCurrenciesSubject: BehaviorSubject<List<Currency>> =
        BehaviorSubject.createDefault(emptyList())

    private val currenciesSubject: PublishSubject<List<Currency>> =
        PublishSubject.create()

    private val isBaseChangedSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)

    override val base: String get() = baseSubject.value!!

    override val amount: BigDecimal get() = amountSubject.value!!

    override val baseAndAmount: Pair<String, BigDecimal> get() = baseSubject.value!! to amountSubject.value!!

    override val isBaseChanged: Boolean get() = isBaseChangedSubject.value!!

    override val updatedAmountCurrencies: List<Currency> get() = updatedAmountCurrenciesSubject.value!!

    override val observeUpdatedAmountCurrencies: Flowable<List<Currency>> by lazy {
        updatedAmountCurrenciesSubject.toFlowable(BackpressureStrategy.LATEST)
    }

    override val observeCurrencies: Flowable<List<Currency>> by lazy {
        currenciesSubject.toFlowable(BackpressureStrategy.LATEST)
    }

    override val observePerSecond: Flowable<Long> by lazy {
        Flowable.interval(1, DEFAULT_INTERVAL, TimeUnit.SECONDS)
            .startWith(0)
    }

    override fun onBaseAndAmountUpdated(base: String, amount: BigDecimal) {
        baseSubject.onNext(base)
        amountSubject.onNext(amount)
    }

    override fun onAmountUpdated(amount: BigDecimal) {
        amountSubject.onNext(amount)
    }

    override fun onCurrenciesAmountUpdated(currencies: List<Currency>) {
        updatedAmountCurrenciesSubject.onNext(currencies)
    }

    override fun onCurrenciesUpdated(currencies: List<Currency>) {
        currenciesSubject.onNext(currencies)
    }

    override fun onIsBaseChanged(isBaseChanged: Boolean) {
        isBaseChangedSubject.onNext(isBaseChanged)
    }

    override fun clear() {
        updatedAmountCurrenciesSubject.onNext(emptyList())
        currenciesSubject.onNext(emptyList())
    }

    companion object {
        private const val DEFAULT_BASE = "EUR"
        private const val DEFAULT_INTERVAL = 2L
        private val DEFAULT_AMOUNT = BigDecimal(100)
    }


}