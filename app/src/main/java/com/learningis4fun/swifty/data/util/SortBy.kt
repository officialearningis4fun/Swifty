package com.learningis4fun.swifty.data.util

import com.learningis4fun.swifty.data.SortByOption

enum class SortBy(val option: String) {
    SMALLEST_PRICE_FIRST("Smallest price first"),
    HIGHEST_PRICE_FIRST("Highest price first"),
    SMALLEST_WEIGHT_FIRST("Smallest weight first"),
    HIGHEST_WEIGHT_FIRST("Highest weight first"),
    MOST_ITEMS_FIRST("Most items first"),
    LEAST_ITEMS_FIRST("Least items first")
}

object SortByOptions {
    fun getSortByList() = SortBy.values().map {
        SortByOption(it.name, it.option, false)
    }
}

