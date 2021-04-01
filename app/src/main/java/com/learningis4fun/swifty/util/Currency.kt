package com.learningis4fun.swifty.util

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object Currency {

    fun monetize(value : String, prefix: String) : String = monetizeDouble(value, prefix)

    private fun monetizeDouble(value: String, prefix: String): String {
        if (value == ".") return "$prefix ${getDecimalPattern(value)}"
        val parsed = BigDecimal(value.replace(",", ""))
        val formatter = DecimalFormat(
            "$prefix#,###.${getDecimalPattern(value)}",
            DecimalFormatSymbols(Locale.US)
        )
        formatter.roundingMode = RoundingMode.DOWN
        return formatter.format(parsed)
    }

    // 8909.908
    // 8 - 4 - 1
    private fun getDecimalPattern(value: String): String {
        return "00"
    }
}