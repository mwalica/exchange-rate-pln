package ch.walica.exchange_rate_pln.domain.model

import ch.walica.exchange_rate_pln.data.remote.dto.RateDetail

data class ExchangeRatesDetail(
    val code: String,
    val currency: String,
    val rates: List<RateDetail>
)
