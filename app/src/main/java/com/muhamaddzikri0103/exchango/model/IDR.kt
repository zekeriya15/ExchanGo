package com.muhamaddzikri0103.exchango.model

import com.muhamaddzikri0103.exchango.R

class IDR : Currency(
    code = "IDR",
    name = R.string.idr_name,
    symbol = "Rp",
    conversionRates = mapOf(
        "IDR" to 1.00,
        "USD" to 0.000060,
        "EUR" to 0.000055,
        "GBP" to 0.000046,
        "JPY" to 0.0090
    ),
    flagResId = mapOf(
        "IDR" to R.drawable.idr_idr,
        "USD" to R.drawable.idr_usd,
        "EUR" to R.drawable.idr_eur,
        "GBP" to R.drawable.idr_gbp,
        "JPY" to R.drawable.idr_jpy
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