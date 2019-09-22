package com.lung.demo.domain.interactor

import com.lung.demo.TestSchedulersProviderImpl
import com.lung.demo.domain.mapper.CurrencyMapper
import com.lung.demo.domain.repository.CurrenciesRepository
import com.lung.demo.domain.scheduler.SchedulersProvider
import com.lung.demo.domain.status.CurrenciesStatusService
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.*
import java.math.BigDecimal

class CurrenciesInteractorImplTest {

    @Mock
    private lateinit var currenciesRepository: CurrenciesRepository
    @Mock
    private lateinit var currenciesStatusService: CurrenciesStatusService
    @Mock
    private lateinit var currencyMapper: CurrencyMapper
    @Mock
    private lateinit var schedulers: SchedulersProvider

    @InjectMocks
    private lateinit var currenciesInteractorImpl: CurrenciesInteractorImpl

    @Captor
    private lateinit var booleanCaptor: ArgumentCaptor<Boolean>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val testSchedulersProviderImpl = TestSchedulersProviderImpl()
        whenever(schedulers.io).thenReturn(testSchedulersProviderImpl.io)
        whenever(schedulers.computation).thenReturn(testSchedulersProviderImpl.computation)
        whenever(schedulers.ui).thenReturn(testSchedulersProviderImpl.ui)
    }


    @Test
    fun currenciesInteractor_observePolling_emitNewBaseCurrencies() {
        // given
        whenever(currenciesStatusService.observePerSecond).thenReturn(Flowable.just(1))
        whenever(currenciesRepository.getActualCurrencies(any(), any())).thenReturn(
            Single.just(
                emptyList()
            )
        )
        whenever(currenciesStatusService.baseAndAmount).thenReturn("EUR" to BigDecimal(100))
        whenever(currenciesStatusService.isBaseChanged).thenReturn(true)

        //when

        currenciesInteractorImpl.observePolling.blockingFirst()


        // then
        verify(currenciesStatusService, times(1)).observePerSecond
        verify(currenciesRepository, times(1)).getActualCurrencies(any(), any())
        verify(currenciesStatusService, times(1)).onCurrenciesUpdated(any())
        verify(currenciesStatusService, never()).onCurrenciesAmountUpdated(any())
        verify(currenciesStatusService, times(1)).onIsBaseChanged(booleanCaptor.capture())
        val capturedBoolean = booleanCaptor.value

        Assert.assertEquals(false, capturedBoolean)

    }

    @Test
    fun currenciesInteractor_observePolling_emitNewAmountCurrencies() {
        // given
        whenever(currenciesStatusService.observePerSecond).thenReturn(Flowable.just(1))
        whenever(currenciesRepository.getActualCurrencies(any(), any())).thenReturn(
            Single.just(
                emptyList()
            )
        )
        whenever(currenciesStatusService.baseAndAmount).thenReturn("EUR" to BigDecimal(100))
        whenever(currenciesStatusService.isBaseChanged).thenReturn(false)

        //when

        currenciesInteractorImpl.observePolling.blockingFirst()


        // then
        verify(currenciesStatusService, times(1)).observePerSecond
        verify(currenciesRepository, times(1)).getActualCurrencies(any(), any())
        verify(currenciesStatusService, never()).onCurrenciesUpdated(any())
        verify(currenciesStatusService, times(1)).onCurrenciesAmountUpdated(any())
        verify(currenciesStatusService, times(1)).onIsBaseChanged(booleanCaptor.capture())
        val capturedBoolean = booleanCaptor.value

        Assert.assertEquals(false, capturedBoolean)

    }

    @Test
    fun currenciesInteractor_onBaseAndAmountUpdated() {
        // given
        val base = "EUR"
        val amount = BigDecimal.ONE

        //when
        currenciesInteractorImpl.onBaseAndAmountUpdated(base, amount).test()

        // then
        verify(currenciesStatusService, times(1)).onBaseAndAmountUpdated(any(), any())
        verify(currenciesStatusService, times(1)).onIsBaseChanged(any())

    }

    @Test
    fun currenciesInteractor_onAmountUpdated() {
        // given
        val base = "EUR"
        val amount = BigDecimal.ONE

        whenever(currenciesStatusService.updatedAmountCurrencies).thenReturn(emptyList())
        whenever(currenciesStatusService.baseAndAmount).thenReturn(base to amount)

        //when
        currenciesInteractorImpl.onAmountUpdated(amount).test()

        // then
        verify(currenciesStatusService, times(1)).onAmountUpdated(any())
        verify(currenciesStatusService, times(1)).updatedAmountCurrencies
        verify(currenciesStatusService, times(1)).onCurrenciesAmountUpdated(any())

    }


}