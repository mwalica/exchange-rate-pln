package ch.walica.exchange_rate_pln.presentation.exchanges_rates_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.walica.exchange_rate_pln.common.Resource
import ch.walica.exchange_rate_pln.domain.use_case.get_exchange_rates.GetExchangeRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class ExchangeRatesListViewModel @Inject constructor(
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ExchangeRatesListState())
    val state: State<ExchangeRatesListState> = _state

    init {
        getExchangeRates()

    }

    private fun getExchangeRates() {
        getExchangeRatesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ExchangeRatesListState(
                        exchangeRates = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _state.value = ExchangeRatesListState(
                        error = result.msg ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = ExchangeRatesListState(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)

    }

}