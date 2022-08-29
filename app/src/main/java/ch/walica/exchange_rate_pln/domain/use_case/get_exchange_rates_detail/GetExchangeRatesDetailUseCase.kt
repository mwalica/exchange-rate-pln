package ch.walica.exchange_rate_pln.domain.use_case.get_exchange_rates_detail

import ch.walica.exchange_rate_pln.common.Resource
import ch.walica.exchange_rate_pln.data.remote.dto.toExchangeRates
import ch.walica.exchange_rate_pln.data.remote.dto.toExchangeRatesDetail
import ch.walica.exchange_rate_pln.domain.model.ExchangeRates
import ch.walica.exchange_rate_pln.domain.model.ExchangeRatesDetail
import ch.walica.exchange_rate_pln.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetExchangeRatesDetailUseCase @Inject constructor(private val repository: AppRepository) {

    operator fun invoke(code: String): Flow<Resource<ExchangeRatesDetail>> = flow {
        try {
            emit(Resource.Loading<ExchangeRatesDetail>())
            val exchangeRatesDetail =
                repository.getExchangeRatesDetail(code).toExchangeRatesDetail()
            emit(Resource.Success<ExchangeRatesDetail>(data = exchangeRatesDetail))
        } catch (e: HttpException) {
            emit(
                Resource.Error<ExchangeRatesDetail>(
                    msg = e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error<ExchangeRatesDetail>(
                    msg = e.localizedMessage
                        ?: "Couldn't reach server. Check your internet connection"
                )
            )
        }
    }
}
