package ch.walica.exchange_rate_pln.data.repository

import ch.walica.exchange_rate_pln.data.remote.NbpApi
import ch.walica.exchange_rate_pln.data.remote.dto.ExchangeRatesDto
import ch.walica.exchange_rate_pln.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(private val api: NbpApi) : AppRepository {


    override suspend fun getExchangeRates(): List<ExchangeRatesDto> = api.getExchangeRates()


}