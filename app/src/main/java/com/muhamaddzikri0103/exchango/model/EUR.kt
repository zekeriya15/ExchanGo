package com.muhamaddzikri0103.exchango.model

import com.muhamaddzikri0103.exchango.R

class EUR : Currency(
    code = "EUR",
    name = R.string.eur_name,
    symbol = "€",
    conversionRates = mapOf(
        "EUR" to 1.00,
        "USD" to 1.079,
        "IDR" to 18061.78,
        "GBP" to 0.84,
        "JPY" to 161.75
    ),
    flagResId = mapOf(
        "EUR" to R.drawable.eur_eur,
        "USD" to R.drawable.eur_usd,
        "IDR" to R.drawable.eur_idr,
        "GBP" to R.drawable.eur_gbp,
        "JPY" to R.drawable.eur_jpy
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