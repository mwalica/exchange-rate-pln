package ch.walica.exchange_rate_pln.domain.use_case.get_exchange_rates

import ch.walica.exchange_rate_pln.common.Resource
import ch.walica.exchange_rate_pln.data.remote.dto.toExchangeRates
import ch.walica.exchange_rate_pln.domain.model.ExchangeRates
import ch.walica.exchange_rate_pln.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetExchangeRatesUseCase @Inject constructor(private val repository: AppRepository) {
    operator fun invoke(): Flow<Resource<List<ExchangeRates>>> = flow {
        try {
            emit(Resource.Loading<List<ExchangeRates>>())
            val exchangeRates = repository.getExchangeRates().map {
                it.toExchangeRates()
            }
            emit(Resource.Success<List<ExchangeRates>>(data = exchangeRates))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<ExchangeRates>>(
                    msg = e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error<List<ExchangeRates>>(
                    msg = e.localizedMessage
                        ?: "Couldn't reach server. Check your internet connection"
                )
            )
        }
    }
}