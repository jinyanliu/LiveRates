package com.lung.demo.data.remote

import com.squareup.moshi.Json

class CurrenciesResponse(
    @Json(name = "rates") val rates: Map<String, Double>
)