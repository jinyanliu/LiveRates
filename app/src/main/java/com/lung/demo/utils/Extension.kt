package com.lung.demo.utils

import java.math.BigDecimal

fun String?.toAmount(): BigDecimal {
    return if (this.isNullOrBlank() || this == ".") {
        BigDecimal.ZERO
    } else this.toBigDecimal()
}
