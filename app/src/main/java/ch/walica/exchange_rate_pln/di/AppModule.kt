package ch.walica.exchange_rate_pln.di

import android.app.Application
import androidx.room.Room
import ch.walica.exchange_rate_pln.common.Constants
import ch.walica.exchange_rate_pln.data.remote.NbpApi
import ch.walica.exchange_rate_pln.data.repository.AppRepositoryImpl
import ch.walica.exchange_rate_pln.data.repository.CurrencyRepositoryImpl
import ch.walica.exchange_rate_pln.data.room.CurrencyDatabase
import ch.walica.exchange_rate_pln.domain.repository.AppRepository
import ch.walica.exchange_rate_pln.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNbpApi(): NbpApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NbpApi::class.java)
    }

    @Provides
    @Singleton
    fun providesAppRepository(api: NbpApi): AppRepository = AppRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideCurrencyDatabase(app: Application): CurrencyDatabase {
        return Room.databaseBuilder(
            app,
            CurrencyDatabase::class.java,
            "currency_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(db: CurrencyDatabase): CurrencyRepository =
        CurrencyRepositoryImpl(db.dao)

}