package ch.walica.exchange_rate_pln.domain.model

import ch.walica.exchange_rate_pln.data.remote.dto.Rate


data class ExchangeRates(
    val effectiveDate: String,
    val rates: List<Rate>
)
