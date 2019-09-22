package com.lung.demo.domain.mapper

import com.lung.demo.data.local.CurrenciesLocalResourcesServiceImpl
import com.lung.demo.domain.exception.IllegalParamException
import com.lung.demo.domain.model.Currency
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import org.junit.rules.ExpectedException
import org.junit.Rule
import java.math.BigDecimal
import java.math.RoundingMode


class CurrencyMapperTest {

    @InjectMocks
    private lateinit var currencyMapper: CurrencyMapper

    @Rule
    @JvmField
    val thrown = ExpectedException.none()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun currencyMapper_map() {

        //given
        val name = "EUR"
        val rate = BigDecimal(1.5)
        val description = "United States Dollar"
        val img = "${CurrenciesLocalResourcesServiceImpl.BASE_IMAGE_URL}eur.png"
        val amount = BigDecimal.ONE
        val expected = Currency(name, description, img, rate, amount)

        //when
        val actual = currencyMapper.map(name, description, img, rate, amount)

        //then
        assertEquals(expected, actual)
    }

    @Test
    fun currencyMapper_mapAmount() {
        //given
        val rate = BigDecimal(1.55555555555)
        val amount = BigDecimal.ONE
        val expected = rate.setScale(4, RoundingMode.HALF_UP).stripTrailingZeros()

        //when
        val actual = currencyMapper.mapAmount(rate, amount)

        //then
        assertEquals(expected, actual)
    }


    @Test
    fun currencyMapper_mapAmount_ThrowsRateAmountError() {
        thrown.expect(IllegalParamException::class.java)
        thrown.expectMessage("The params: rate amount can't be negative!")

        currencyMapper.mapAmount(BigDecimal(-1), BigDecimal(-1))

    }
}