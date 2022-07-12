package ch.walica.exchange_rate_pln.data.remote.dto


data class Rate(
    val code: String,
    val currency: String,
    val mid: Double
)