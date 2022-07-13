package ch.walica.exchange_rate_pln.domain.repository

import ch.walica.exchange_rate_pln.domain.model.Currency
import kotlinx.coroutines.flow.Flow


interface CurrencyRepository {
    suspend fun insertCurrency(currency: Currency)

    suspend fun deleteCurrency(currency: Currency)

    fun getCurrency(): Flow<List<Currency>>
}