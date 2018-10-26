package com.github.krystiankowalik.splitme.mobile.util

import org.joda.time.DateTime
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

object StringUtilities {

    fun formatCurrency(number: BigDecimal, currency: String): String {
        return NumberFormat.getCurrencyInstance().format(number) + " " + currency
    }

    fun formatDate(date: DateTime): String {
        return date.toString("yyyy-MM-dd", Locale.US)
    }
    fun formatDate(date: org.joda.time.LocalDate): String {
        return date.toString("yyyy-MM-dd", Locale.US)
    }
}
