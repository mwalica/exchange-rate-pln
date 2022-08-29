package ch.walica.exchange_rate_pln.presentation.exchanges_rates_detail

import ch.walica.exchange_rate_pln.domain.model.ExchangeRatesDetail

data class ExchangeRatesDetailState(
    val exchangeRatesDetail: ExchangeRatesDetail? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
