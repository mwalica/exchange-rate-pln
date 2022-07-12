package ch.walica.exchange_rate_pln.data.remote

import ch.walica.exchange_rate_pln.data.remote.dto.ExchangeRatesDto
import retrofit2.http.GET

interface NbpApi {

    @GET("last/2/?format=json")
    suspend fun getExchangeRates(): List<ExchangeRatesDto>

}