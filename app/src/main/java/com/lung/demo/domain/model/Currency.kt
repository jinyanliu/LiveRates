package com.lung.demo.domain.model

import java.math.BigDecimal

data class Currency(
    val name: String,
    val description: String,
    val img: String,
    val rate: BigDecimal,
    val amount: BigDecimal
)
