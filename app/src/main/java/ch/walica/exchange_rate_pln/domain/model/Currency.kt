package ch.walica.exchange_rate_pln.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Currency(
    val currency: String,
    @PrimaryKey
    val code: String
)

fun toCurrencyList() : List<Currency> {
    return listOf(
        Currency("dolar amerykański", "USD"),
        Currency("dolar australijski", "AUD"),
        Currency("dolar kanadyjski", "CAD"),
        Currency("euro", "EUR"),
        Currency("forint (Węgry)", "HUF"),
        Currency("funt szterling", "GBP"),
        Currency("frank szwajcarski", "CHF"),
        Currency("hrywna (Ukraina)", "UAH"),
        Currency("korona czeska", "CZK"),
        Currency("korona duńska", "DKK"),
        Currency("korona norweska", "NOK"),
        Currency("korona szwedzka", "SEK"),
        Currency("kuna (Chorwacja)", "HRK"),
        Currency("lew (Bułgaria)", "BGN"),
        Currency("yuan renminbi (Chiny)", "CNY")
    )
}