package ch.walica.exchange_rate_pln.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ch.walica.exchange_rate_pln.domain.model.Currency

@Database(
    entities = [Currency::class],
    version = 1
)
abstract class CurrencyDatabase: RoomDatabase() {
    abstract val dao: CurrencyDao
}