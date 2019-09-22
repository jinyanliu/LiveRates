package com.lung.demo.domain.mapper

import com.lung.demo.domain.exception.IllegalParamException
import com.lung.demo.domain.model.Currency
import dagger.Reusable
import timber.log.Timber
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@Reusable
class CurrencyMapper @Inject constructor() {

    fun map(
        name: String,
        description: String,
        img: String,
        rate: BigDecimal,
        amount: BigDecimal
    ): Currency {
        return Currency(
            name = name,
            description = description,
            img = img,
            rate = rate,
            amount = amount
        )
    }

    fun mapAmount(rate: BigDecimal, amount: BigDecimal): BigDecimal {

        var illegalParams = ""

        if (rate < BigDecimal.ZERO) {
            illegalParams += " rate"
        }

        if (amount < BigDecimal.ZERO) {
            illegalParams += " amount"
        }

        if (illegalParams.isNotEmpty())
            throw  IllegalParamException(illegalParams)

        val convertAmount: BigDecimal

        try {
            convertAmount = rate.multiply(amount).multiply(BigDecimal(100))
                .divide(BigDecimal(100), 4, RoundingMode.HALF_UP).stripTrailingZeros()
        } catch (e: Exception) {
            Timber.e(e)
            return BigDecimal.ZERO
        }

        return convertAmount
    }
}
