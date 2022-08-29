package ch.walica.exchange_rate_pln.domain.repository

import ch.walica.exchange_rate_pln.data.remote.dto.ExchangeRatesDetailDto
import ch.walica.exchange_rate_pln.data.remote.dto.ExchangeRatesDto

interface AppRepository {

    suspend fun getExchangeRates(): List<ExchangeRatesDto>

    suspend fun getExchangeRatesDetail(code: String) : ExchangeRatesDetailDto
}