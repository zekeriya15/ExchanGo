package com.muhamaddzikri0103.exchango.model

import com.muhamaddzikri0103.exchango.R

class JPY : Currency(
    code = "JPY",
    name = R.string.jpy_name,
    symbol = "¥",
    conversionRates = mapOf(
        "JPY" to 1.00,
        "USD" to 0.0066,
        "IDR" to 111.60,
        "EUR" to 0.0062,
        "GBP" to 0.0052
    ),
    flagResId = mapOf(
        "JPY" to R.drawable.jpy_jpy,
        "USD" to R.drawable.jpy_usd,
        "IDR" to R.drawable.jpy_idr,
        "EUR" to R.drawable.jpy_eur,
        "GBP" to R.drawable.jpy_gbp
    )
) {
    override fun convert(amount: Double, toCurrency: String): Double {
        val conversionRate = conversionRates[toCurrency]
        return if (conversionRate != null) {
            amount * conversionRate
        } else {
            throw IllegalArgumentException("Unsupported currency: $toCurrency")
        }
    }
}