package ch.walica.exchange_rate_pln.presentation.add_currency_screen

data class AddCurrencyState(
    val isExpanded: Boolean = false,
    val selectedCurrency: String = "dolar amerykański",
    val selectedCode: String = "USD"
)