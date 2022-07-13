package ch.walica.exchange_rate_pln.presentation.add_currency_screen

import ch.walica.exchange_rate_pln.domain.model.Currency

sealed class ListOperation {
    data class AddCurrencyCode(val currency: Currency) : ListOperation()
    data class DeleteCurrencyCode(val currency: Currency) : ListOperation()
    data class Expanded(val bool: Boolean) : ListOperation()
    data class SelectedCurrency(val currency: String) : ListOperation()
    data class SelectedCode(val code: String) : ListOperation()
}
