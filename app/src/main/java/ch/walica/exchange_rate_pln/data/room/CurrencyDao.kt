package ch.walica.exchange_rate_pln.data.room

import androidx.room.*
import ch.walica.exchange_rate_pln.domain.model.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: Currency)

    @Delete
    suspend fun deleteCurrency(currency: Currency)

    @Query("SELECT * FROM currency")
    fun getCurrency(): Flow<List<Currency>>
}