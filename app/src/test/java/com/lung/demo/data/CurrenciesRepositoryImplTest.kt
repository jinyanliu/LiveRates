package com.lung.demo.data

import com.lung.demo.App
import com.lung.demo.data.local.CurrenciesLocalResourcesServiceImpl
import com.lung.demo.data.local.CurrenciesLocalResourcesServiceImpl.Companion.BASE_IMAGE_URL
import com.lung.demo.data.remote.CurrenciesApiService
import com.lung.demo.data.remote.CurrenciesResponse
import com.lung.demo.domain.mapper.CurrencyMapper
import com.lung.demo.domain.model.Currency
import com.lung.demo.domain.repository.CurrenciesLocalResourcesService
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.math.BigDecimal

@Config(sdk = [28], application = App::class)
@RunWith(RobolectricTestRunner::class)
class CurrenciesRepositoryImplTest {

    @Mock
    private lateinit var currenciesApiService: CurrenciesApiService

    private lateinit var currenciesLocalResourcesService: CurrenciesLocalResourcesService

    private lateinit var currencyMapper: CurrencyMapper

    private lateinit var currenciesRepositoryImpl: CurrenciesRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        currenciesLocalResourcesService =
            CurrenciesLocalResourcesServiceImpl(RuntimeEnvironment.application.applicationContext)
        currencyMapper = CurrencyMapper()
        currenciesRepositoryImpl = CurrenciesRepositoryImpl(
            currenciesApiService,
            currenciesLocalResourcesService,
            currencyMapper
        )
    }

    @Test
    fun currenciesRepository_getActualCurrencies() {
        // given

        val base = "EUR"
        val baseAmount = BigDecimal(100)
        val rates = mapOf("USD" to 1.5)

        val mockResponse = Single.just(CurrenciesResponse(rates))

        val expectCurrency =
            Currency(
                "USD",
                "United States Dollar",
                "${BASE_IMAGE_URL}usd.png",
                BigDecimal(1.5),
                BigDecimal.ONE
            )
        val expectBase =
            Currency(
                base,
                "Euro",
                "${BASE_IMAGE_URL}eur.png",
                BigDecimal.ONE,
                baseAmount
            )

        val expectCurrencies = listOf(expectBase) + expectCurrency

        whenever(currenciesApiService.getCurrencies(any())).thenReturn(mockResponse)

        //when
        val testObserver = currenciesRepositoryImpl.getActualCurrencies(base, baseAmount).test()

        // then

        testObserver.assertValue(expectCurrencies)

    }


}