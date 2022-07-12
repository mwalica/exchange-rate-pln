package ch.walica.exchange_rate_pln.presentation.exchanges_rates_list

import ch.walica.exchange_rate_pln.data.remote.dto.Rate
import ch.walica.exchange_rate_pln.domain.model.ExchangeRates

data class ExchangeRatesListState(
    val exchangeRates: List<ExchangeRates> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)