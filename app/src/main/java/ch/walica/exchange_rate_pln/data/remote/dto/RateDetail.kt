package ch.walica.exchange_rate_pln.data.remote.dto

data class RateDetail(
    val effectiveDate: String,
    val mid: Double,
    val no: String
)