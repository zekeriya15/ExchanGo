package com.muhamaddzikri0103.exchango.model

import com.muhamaddzikri0103.exchango.R

class USD {
    val name: String = "USD"
    val symbol: String = "$"
    val conversionRates: Map<String, Double> = mapOf(
        "USD" to 1.00,
        "IDR" to 16730.40,
        "EUR" to 0.93,
        "GBP" to 0.78,
        "JPY" to 149.92
    )
    val flagResId: Map<String, Int> = mapOf(
         "USD" to R.drawable.usd_usd,
         "IDR" to R.drawable.usd_idr,
         "EUR" to R.drawable.usd_eur,
         "GBP" to R.drawable.usd_gbp,
         "JPY" to R.drawable.usd_jpy
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