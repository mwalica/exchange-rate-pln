package ch.walica.exchange_rate_pln.data.remote

import ch.walica.exchange_rate_pln.data.remote.dto.ExchangeRatesDetailDto
import ch.walica.exchange_rate_pln.data.remote.dto.ExchangeRatesDto
import retrofit2.http.GET
import retrofit2.http.Path

interface NbpApi {

    @GET("tables/a/last/2/?format=json")
    suspend fun getExchangeRates(): List<ExchangeRatesDto>

    @GET("rates/a/{code}/last/5/?format=json")
    suspend fun getExchangeRatesDetail(@Path("code") code: String): ExchangeRatesDetailDto

}