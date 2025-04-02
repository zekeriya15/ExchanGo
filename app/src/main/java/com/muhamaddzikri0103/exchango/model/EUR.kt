package com.muhamaddzikri0103.exchango.model

import com.muhamaddzikri0103.exchango.R

class EUR {
    val name: String = "EUR"
    val symbol: String = "€"
    val conversionRates: Map<String, Double> = mapOf(
        "EUR" to 1.00,
        "USD" to 1.079,
        "IDR" to 18061.78,
        "GBP" to 0.84,
        "JPY" to 161.75
    )
    val flagResId: Map<String, Int> = mapOf(
        "EUR" to R.drawable.eur_eur,
        "USD" to R.drawable.eur_usd,
        "IDR" to R.drawable.eur_idr,
        "GBP" to R.drawable.eur_gbp,
        "JPY" to R.drawable.eur_jpy
    )

    fun convert(amount: Double, toCurrency: String): Double {
        val conversionRate = conversionRates[toCurrency]
        return if (conversionRate != null) {
            amount * conversionRate
        } else {
            throw IllegalArgumentException("Unsupported currency: $toCurrency")
        }
    }
}