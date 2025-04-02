package com.muhamaddzikri0103.exchango.model

abstract class Currency(
    val name: String,
    val symbol: String,
    val conversionRates: Map<String, Double>,
    val flagResId: Map<String, Int>
) {
    abstract fun convert(amount: Double, toCurrency: String): Double
}