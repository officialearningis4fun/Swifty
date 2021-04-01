package com.learningis4fun.swifty.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object Util {

    fun dateFromLong(date: Long): String {
        return if (date == 0L) "" else DateFormat.getDateInstance(DateFormat.SHORT)
            .format(Date(date))
    }

    fun currentDate(): Long {
        return Calendar.getInstance().timeInMillis
    }

    fun getTotalPrice(pricePerItem: Double, numberOfItems: Int): Double {
        return pricePerItem * numberOfItems
    }

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
        val decimals = decimalsString.toIntOrNull()
        return if (!showDecimals(decimals)) {
            amountString.substringBefore('.')
        } else {
            amountString
        }
    }

    private fun showDecimals(decimals: Int?): Boolean {
        return if (decimals != null) decimals > 0 else true
    }

    /**
     * 0.0 -> 0.00
     * 12.0, 12.00
     * 123.0 -> 123.00
     * 1234.0 -> 1,234.00
     * 12345.0 -> 12,345.00
     * 123456.0 -> 123,456.00
     * 1234567.0 -> 1,234,567.00
     * support up to 9 million
     */

    fun hideKeyBoard(activity: Activity?, view: View?) {

        activity?.let {
            val inputMethodManager: InputMethodManager =
                it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (inputMethodManager.isActive) {
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
            }
        }
    }

    fun showKeyboard(view: View?) {
        view?.let {
            it.requestFocus()
            if (it.requestFocus()) {
                val inputMethodManager: InputMethodManager =
                    it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }
}