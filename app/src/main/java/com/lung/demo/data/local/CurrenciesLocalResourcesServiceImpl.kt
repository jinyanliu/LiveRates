package com.lung.demo.data.local

import android.content.Context
import com.lung.demo.R
import com.lung.demo.domain.repository.CurrenciesLocalResourcesService
import java.util.*
import javax.inject.Inject

class CurrenciesLocalResourcesServiceImpl @Inject constructor(private val context: Context) :
    CurrenciesLocalResourcesService {

    private val descriptions = context.run {
        mapOf(
            "AUD" to getString(R.string.australian),
            "BGN" to getString(R.string.bulgaria),
            "BRL" to getString(R.string.brazilian),
            "CAD" to getString(R.string.canadian),
            "CHF" to getString(R.string.swiss),
            "CNY" to getString(R.string.chinese),
            "CZK" to getString(R.string.czech),
            "DKK" to getString(R.string.denmark),
            "GBP" to getString(R.string.united_kingdom),
            "HKD" to getString(R.string.hong_kong),
            "HRK" to getString(R.string.croatia),
            "HUF" to getString(R.string.hungary),
            "IDR" to getString(R.string.indonesia),
            "ILS" to getString(R.string.israel),
            "INR" to getString(R.string.india),
            "ISK" to getString(R.string.iceland),
            "JPY" to getString(R.string.japan),
            "KRW" to getString(R.string.south_korea),
            "MXN" to getString(R.string.mexico),
            "MYR" to getString(R.string.malaysia),
            "NOK" to getString(R.string.norway),
            "NZD" to getString(R.string.new_zealand),
            "PHP" to getString(R.string.philippines),
            "PLN" to getString(R.string.poland),
            "RON" to getString(R.string.romania),
            "RUB" to getString(R.string.russia),
            "SEK" to getString(R.string.sweden),
            "SGD" to getString(R.string.singapore),
            "THB" to getString(R.string.thailand),
            "TRY" to getString(R.string.turkey),
            "USD" to getString(R.string.usa),
            "ZAR" to getString(R.string.south_africa),
            "EUR" to getString(R.string.euro)
        )
    }

    override fun getDescriptionAndImage(rate: String): Pair<String, String> {
        val description = descriptions[rate]
        return if (description != null) {
            description to imageForRate(rate)
        } else {
            context.getString(R.string.unknown_currency) to ""
        }
    }

    private fun imageForRate(rate: String) = "$BASE_IMAGE_URL${rate.toLowerCase(Locale.US)}.png"

    companion object {
        const val BASE_IMAGE_URL =
            "https://raw.githubusercontent.com/transferwise/currency-flags/master/src/flags/"
    }

}