package ch.walica.exchange_rate_pln.data.remote.dto

import ch.walica.exchange_rate_pln.domain.model.ExchangeRatesDetail

data class ExchangeRatesDetailDto(
    val code: String,
    val currency: String,
    val rates: List<RateDetail>,
    val table: String
)

fun ExchangeRatesDetailDto.toExchangeRatesDetail(): ExchangeRatesDetail =
    ExchangeRatesDetail(code = code, currency = currency, rates = rates)