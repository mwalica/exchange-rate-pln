package ch.walica.exchange_rate_pln.presentation.exchanges_rates_list.components

import android.app.Activity
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ch.walica.exchange_rate_pln.R
import ch.walica.exchange_rate_pln.domain.model.Currency
import ch.walica.exchange_rate_pln.presentation.Screen
import ch.walica.exchange_rate_pln.presentation.add_currency_screen.AddCurrencyViewModel
import ch.walica.exchange_rate_pln.presentation.add_currency_screen.ListOperation
import ch.walica.exchange_rate_pln.presentation.exchanges_rates_list.ExchangeRatesListViewModel
import ch.walica.exchange_rate_pln.presentation.ui.theme.*

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ExchangeRatesListScreen(
    navController: NavController,
    viewModel: ExchangeRatesListViewModel = hiltViewModel(),
    addCurrencyViewModel: AddCurrencyViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as? Activity
    var showMenu by remember {
        mutableStateOf(false)
    }
    val state by viewModel.state
    val currencies = addCurrencyViewModel.currencies.collectAsState(initial = emptyList())
    val mutableCurrenciesCodeList = mutableListOf<String>()
    currencies.value.forEach { currency ->
        mutableCurrenciesCodeList.add(currency.code)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(id = R.string.title_exchange_rates_list_1) + " ")
                            withStyle(style = SpanStyle(color = color5)) {
                                append(stringResource(id = R.string.title_exchange_rates_list_2) + " ")
                            }
                            append(stringResource(id = R.string.title_exchange_rates_list_3))
                        }.toUpperCase(),
                        style = MaterialTheme.typography.h1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                actions = {
                    IconButton(
                        onClick = { showMenu = !showMenu }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "drop down menu"
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(onClick = { activity?.finish() }) {
                            Text(text = stringResource(id = R.string.close_app))
                        }
                    }
                }

            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.exchangeRates.isNotEmpty()) {
                Text(
                    text = "(${state.exchangeRates[1].effectiveDate})",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Red)
            ) {
                if (state.exchangeRates.isNotEmpty()) {

                    val configuration = LocalConfiguration.current
                    when (configuration.orientation) {
                        Configuration.ORIENTATION_PORTRAIT -> {
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
                                                            state.exchangeRates[1].rates[ind].currency,
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
                        Configuration.ORIENTATION_LANDSCAPE -> {

                            LazyVerticalGrid(
                                modifier = Modifier.fillMaxSize(),
                                cells = GridCells.Fixed(2)
                            ) {
                                items(state.exchangeRates[1].rates.size) { ind ->

                                    if (mutableCurrenciesCodeList.contains(state.exchangeRates[1].rates[ind].code)) {
                                        RateItem(
                                            state.exchangeRates[1].rates[ind],
                                            state.exchangeRates[0].rates[ind]
                                        )
                                    }

                                }
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
                modifier = Modifier.padding(12.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate(Screen.AddCurrencyScreen.route)
                    }
                ) {
                    Text(
                        text = (stringResource(id = R.string.title_add_currency_1) + " " + stringResource(
                            id = R.string.title_add_currency_2
                        )).uppercase()
                    )
                }
            }
        }

    }

}