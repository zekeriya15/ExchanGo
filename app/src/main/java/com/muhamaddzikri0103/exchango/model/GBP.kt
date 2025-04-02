package com.muhamaddzikri0103.exchango.model

import com.muhamaddzikri0103.exchango.R

class GBP : Currency(
    code = "GBP",
    name = R.string.gbp_name,
    symbol = "£",
    conversionRates = mapOf(
    "GBP" to 1.00,
    "USD" to 1.30,
    "IDR" to 21600.148,
    "EUR" to 1.20,
    "JPY" to 193.63
    ),
    flagResId = mapOf(
    "GBP" to R.drawable.gbp_gbp,
    "USD" to R.drawable.gbp_usd,
    "IDR" to R.drawable.gbp_idr,
    "EUR" to R.drawable.gbp_eur,
    "JPY" to R.drawable.gbp_jpy
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