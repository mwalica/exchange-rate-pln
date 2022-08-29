package ch.walica.exchange_rate_pln.presentation.exchanges_rates_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.walica.exchange_rate_pln.common.Constants
import ch.walica.exchange_rate_pln.common.Resource
import ch.walica.exchange_rate_pln.domain.use_case.get_exchange_rates_detail.GetExchangeRatesDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ExchangeRatesDetailViewModel @Inject constructor(
    private val getExchangeRatesDetailUseCase: GetExchangeRatesDetailUseCase,
    saveStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(ExchangeRatesDetailState())
    val state: State<ExchangeRatesDetailState> = _state

    init {
        saveStateHandle.get<String>(Constants.PARAM_CODE)?.let { code ->
            getExchangeRatesDetail(code)
        }
    }

    private fun getExchangeRatesDetail(code: String) {
        getExchangeRatesDetailUseCase(code).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ExchangeRatesDetailState(
                        exchangeRatesDetail = result.data
                    )
                }
                is Resource.Error -> {
                    _state.value = ExchangeRatesDetailState(
                        error = result.msg ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = ExchangeRatesDetailState(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}