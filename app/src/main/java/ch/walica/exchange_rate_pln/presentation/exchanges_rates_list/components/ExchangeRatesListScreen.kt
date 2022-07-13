package ch.walica.exchange_rate_pln.presentation.exchanges_rates_list.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ch.walica.exchange_rate_pln.domain.model.Currency
import ch.walica.exchange_rate_pln.presentation.Screen
import ch.walica.exchange_rate_pln.presentation.add_currency_screen.AddCurrencyViewModel
import ch.walica.exchange_rate_pln.presentation.add_currency_screen.ListOperation
import ch.walica.exchange_rate_pln.presentation.exchanges_rates_list.ExchangeRatesListViewModel
import ch.walica.exchange_rate_pln.presentation.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExchangeRatesListScreen(
    navController: NavController,
    viewModel: ExchangeRatesListViewModel = hiltViewModel(),
    addCurrencyViewModel: AddCurrencyViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val currencies = addCurrencyViewModel.currencies.collectAsState(initial = emptyList())
    val mutableCurrenciesCodeList = mutableListOf<String>()
    currencies.value.forEach { currency ->
        mutableCurrenciesCodeList.add(currency.code)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                append("Średnie")
                withStyle(style = SpanStyle(color = color5)) {
                    append(" kursy")
                }
                append(" walut")
            }.toUpperCase(),
            style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(top = 22.dp, bottom = 2.dp)
        )
        if (state.exchangeRates.isNotEmpty()) {
            Text(
                text = "(${state.exchangeRates[1].effectiveDate})",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
        Box(
            modifier = Modifier.weight(1f)
        ) {
            if (state.exchangeRates.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.exchangeRates[1].rates.size) { ind ->

                        val dismissState = rememberDismissState(
                            confirmStateChange = {
                                if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                                    addCurrencyViewModel.onAction(
                                        ListOperation.DeleteCurrencyCode(
                                            Currency(
                                                state.exchangeRates[1].rates[ind].code,
                                                state.exchangeRates[1].rates[ind].code
                                            )
                                        )
                                    )
                                }
                                true
                            }
                        )

                        if (mutableCurrenciesCodeList.contains(state.exchangeRates[1].rates[ind].code)) {
                            SwipeToDismiss(
                                state = dismissState,
                                directions = setOf(
                                    DismissDirection.EndToStart,
                                    DismissDirection.StartToEnd
                                ),
                                background = {},
                                dismissContent = {
                                    RateItem(
                                        state.exchangeRates[1].rates[ind],
                                        state.exchangeRates[0].rates[ind]
                                    )
                                }
                            )

                        }

                    }
                }
            }

            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Button(onClick = {
                navController.navigate(Screen.AddCurrencyScreen.route)
            }) {
                Text(text = "Dodaj walutę".uppercase())
            }
        }
    }

}