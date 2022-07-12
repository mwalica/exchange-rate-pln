package ch.walica.exchange_rate_pln.data.remote.dto

import ch.walica.exchange_rate_pln.domain.model.ExchangeRates


data class ExchangeRatesDto(
    val effectiveDate: String,
    val no: String,
    val rates: List<Rate>,
    val table: String
)

fun ExchangeRatesDto.toExchangeRates(): ExchangeRates =
    ExchangeRates(effectiveDate = effectiveDate, rates = rates)
