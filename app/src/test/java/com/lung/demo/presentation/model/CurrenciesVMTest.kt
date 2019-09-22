package com.lung.demo.presentation.model

import com.lung.demo.domain.interactor.CurrenciesInteractor
import com.lung.demo.presentation.animation.CurrencyAnimationsUpdateService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import org.junit.Rule
import java.math.BigDecimal

class CurrenciesVMTest {

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var interactor: CurrenciesInteractor
    @Mock
    private lateinit var currencyAnimationsUpdateService: CurrencyAnimationsUpdateService
    @Mock
    private lateinit var errorObserver: Observer<Unit>
    @InjectMocks
    private lateinit var currenciesVM: CurrenciesVM
    @Captor
    private lateinit var booleanCaptor: ArgumentCaptor<Boolean>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        booleanCaptor = ArgumentCaptor.forClass(Boolean::class.java)
        currenciesVM.errorEvent.observeForever(errorObserver)
    }

    @Test
    fun currenciesVM_currencyCanSelected() {
        //given
        val base = "EUR"
        val amount = BigDecimal.ONE
        whenever(currencyAnimationsUpdateService.isAnimationsEnd).thenReturn(true)
        whenever(interactor.onBaseAndAmountUpdated(any(), any())).thenReturn(Completable.complete())

        //when
        currenciesVM.currencySelected(0, base, amount)

        //then
        verify(currencyAnimationsUpdateService, times(1)).onIsAnimationsEnd(booleanCaptor.capture())
        val capturedBoolean = booleanCaptor.value
        Assert.assertEquals(false, capturedBoolean)

    }

    @Test
    fun currenciesVM_currencyCanSelected_error() {
        //given
        val base = "EUR"
        val amount = BigDecimal.ONE
        val throwable = Throwable()
        whenever(currencyAnimationsUpdateService.isAnimationsEnd).thenReturn(true)
        whenever(interactor.onBaseAndAmountUpdated(any(), any())).thenReturn(
            Completable.error(
                throwable
            )
        )

        //when
        currenciesVM.currencySelected(0, base, amount)

        //then
        verify(errorObserver).onChanged(Unit)
        verify(currencyAnimationsUpdateService, times(1)).onIsAnimationsEnd(booleanCaptor.capture())
        val capturedBoolean = booleanCaptor.value
        Assert.assertEquals(false, capturedBoolean)


    }

    @Test
    fun currenciesVM_currencyCantSelected() {
        //given
        val base = "EUR"
        val amount = BigDecimal.ONE
        whenever(currencyAnimationsUpdateService.isAnimationsEnd).thenReturn(false)

        //when
        currenciesVM.currencySelected(0, base, amount)

        //then
        verify(currencyAnimationsUpdateService, never()).onIsAnimationsEnd(any())

    }

}