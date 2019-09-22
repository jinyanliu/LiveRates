package com.lung.demo.presentation.model

import android.os.Handler
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.lung.demo.BuildConfig
import com.lung.demo.di.scope.CurrenciesScope
import com.lung.demo.domain.interactor.CurrenciesInteractor
import com.lung.demo.domain.model.Currency
import com.lung.demo.presentation.base.BaseViewModel
import com.lung.demo.presentation.animation.CurrencyAnimationsUpdateService
import com.lung.demo.utils.EspressoIdlingResource
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.math.BigDecimal
import javax.inject.Inject

@CurrenciesScope
class CurrenciesVM @Inject constructor(
    interactor: CurrenciesInteractor,
    private val currencyAnimationsUpdateService: CurrencyAnimationsUpdateService
) :
    BaseViewModel<CurrenciesInteractor>(interactor) {

    @JvmField
    val showLoader = ObservableBoolean(false)

    @JvmField
    val currenciesChanges = MutableLiveData<List<Currency>>()
    @JvmField
    val amountChanges = MutableLiveData<List<Currency>>()
    @JvmField
    val baseChanged = MutableLiveData<Int>()
    @JvmField
    val errorEvent = MutableLiveData<Unit>()

    private var pollingDisposables: Disposable? = null
    private var baseCurrenciesChangeDisposables: Disposable? = null

    override fun created() {
        if (BuildConfig.DEBUG) {
            uiTestDelay()
        }
        subscribe()
    }

    override fun destroyed() {
        interactor.clear()
        super.destroyed()
    }

    private fun subscribe() {
        subscribePolling()
        subscribeBaseAndAmountChanged()
        subscribeAmountChanged()
    }

    fun retry() {
        interactor.clear()
        disposables.clear()
        subscribe()
    }

    private fun subscribePolling() {
        pollingDisposables?.dispose()
        pollingDisposables = interactor.observePolling
            .subscribeBy(
                onError = {
                    Timber.e(it)
                    hideLoading()
                    errorEvent.postValue(Unit)
                }
            )
        disposables += pollingDisposables!!
    }


    private fun subscribeBaseAndAmountChanged() {
        baseCurrenciesChangeDisposables?.dispose()
        baseCurrenciesChangeDisposables = interactor.observeCurrencies
            .doOnSubscribe {
                showLoading()
            }
            .subscribeBy(
                onNext = {
                    hideLoading()
                    currenciesChanges.postValue(it)
                },
                onError = {
                    Timber.e(it)
                    hideLoading()
                    errorEvent.postValue(Unit)
                }
            )
        disposables += baseCurrenciesChangeDisposables!!

    }

    private fun subscribeAmountChanged() {
        disposables += interactor.observeUpdatedAmountCurrencies
            .subscribeBy(
                onNext = {
                    hideLoading()
                    amountChanges.postValue(it)
                },
                onError = {
                    Timber.e(it)
                    hideLoading()
                    errorEvent.postValue(Unit)
                }
            )
    }

    fun currencySelected(position: Int, base: String, amount: BigDecimal) {
        if (currencyAnimationsUpdateService.isAnimationsEnd) {
            pollingDisposables?.dispose()
            baseCurrenciesChangeDisposables?.dispose()
            currencyAnimationsUpdateService.onIsAnimationsEnd(false)
            baseChanged.postValue(position)
            disposables += interactor.onBaseAndAmountUpdated(base, amount)
                .subscribeBy(
                    onComplete = {
                        subscribePolling()
                        subscribeBaseAndAmountChanged()
                    },
                    onError = {
                        Timber.e(it)
                        errorEvent.postValue(Unit)
                    }
                )
        }
    }

    fun amountChanged(amount: BigDecimal) {
        if (currencyAnimationsUpdateService.isAnimationsEnd) {
            currencyAnimationsUpdateService.onIsAnimationsEnd(false)
            disposables += interactor.onAmountUpdated(amount)
                .subscribeBy(
                    onError = {
                        Timber.e(it)
                        errorEvent.postValue(Unit)
                    }
                )
        }
    }

    private fun hideLoading() {
        if (showLoader.get()) {
            showLoader.set(false)
        }
    }

    private fun showLoading() {
        if (!showLoader.get()) {
            showLoader.set(true)
        }
    }

    private fun uiTestDelay() {
        EspressoIdlingResource.increment()
        Handler().postDelayed({
            EspressoIdlingResource.decrement()
        }, 2000L)
    }

}