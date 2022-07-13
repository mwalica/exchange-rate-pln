package ch.walica.exchange_rate_pln.presentation.add_currency_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.walica.exchange_rate_pln.domain.model.Currency
import ch.walica.exchange_rate_pln.domain.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCurrencyViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    var state by mutableStateOf(AddCurrencyState())
        private set

    val currencies = currencyRepository.getCurrency()

    fun onAction(action: ListOperation) {
        when (action) {
            is ListOperation.AddCurrencyCode -> delete(action.currency)
            is ListOperation.AddCurrencyCode -> add(action.currency)
            is ListOperation.Expanded -> state = state.copy(
                isExpanded = action.bool
            )
            is ListOperation.SelectedCurrency -> state = state.copy(
                selectedCurrency = action.currency
            )
            is ListOperation.SelectedCode -> state = state.copy(
                selectedCode = action.code
            )
        }
    }

    private fun delete(currency: Currency) {
        viewModelScope.launch {
            currencyRepository.insertCurrency(currency)
        }
    }

    private fun add(currency: Currency) {
        viewModelScope.launch {
            currencyRepository.deleteCurrency(currency)
        }
    }
}