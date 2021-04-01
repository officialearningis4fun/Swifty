package com.learningis4fun.swifty.util

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object Mass {

    fun weightWithUnits(weight: Double): String {

        // mass 9,009.56 g -> 9.01 kg
        // mass 9,009.0 g -> 9.00 kg -> 9kg

        // find numbers after decimal point, if zero, leave out the decimals
        // if the numbers before the decimal point are in multiples of 1,000 convert to kg
        return if (weight >= 1000) {
            // kilograms
            formatWeightForKgs(weight)
        } else {
            // grams
            formatWeightForGrams(weight)
        }
    }

    private fun formatWeightForKgs(weight: Double): String {
        val w = (weight / 1000)
        return "${removeUnnecessaryDecimals(w)} kg"
    }

    private fun formatWeightForGrams(weight: Double): String {
        return "${removeUnnecessaryDecimals(weight)} g"
    }

    fun removeUnnecessaryDecimals(amount: Double): String {
        val amountString = amount.toString()
        val decimalsString = amountString.substringAfter('.', "0")
        val decimals = decimalsString.toIntOrNull() ?: 0
        return if (!showDecimals(decimals)) {
            amountString.substringBefore('.')
        } else {
            formatDecimal(amountString)
        }
    }

    private fun showDecimals(decimals: Int?): Boolean {
        return if (decimals != null) decimals > 0 else true
    }

    private fun formatDecimal(str: String): String {
        if (str == ".") {
            return "0."
        }
        val parsed = BigDecimal(str)
        // example pattern #,###.00
        val formatter = DecimalFormat(
            "#,###." + getDecimalPattern(str),
            DecimalFormatSymbols(Locale.US)
        );
        formatter.roundingMode = RoundingMode.DOWN
        return formatter.format(parsed)
    }

    //.0 or .00
    private fun getDecimalPattern(value: String): String =
        when ((value.length - value.indexOf(".") - 1)) {
            1 -> "0"
            else -> "00"
        }
}