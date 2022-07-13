package ch.walica.exchange_rate_pln.data.repository

import ch.walica.exchange_rate_pln.data.room.CurrencyDao
import ch.walica.exchange_rate_pln.domain.model.Currency
import ch.walica.exchange_rate_pln.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow

class CurrencyRepositoryImpl(private val dao: CurrencyDao) : CurrencyRepository {
    override suspend fun insertCurrency(currency: Currency) {
        dao.insertCurrency(currency)
    }

    override suspend fun deleteCurrency(currency: Currency) {
        dao.deleteCurrency(currency)
    }

    override fun getCurrency(): Flow<List<Currency>> {
        return dao.getCurrency()
    }
}