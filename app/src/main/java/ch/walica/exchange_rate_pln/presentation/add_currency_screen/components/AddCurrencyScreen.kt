package ch.walica.exchange_rate_pln.presentation.add_currency_screen.components

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ch.walica.exchange_rate_pln.R
import ch.walica.exchange_rate_pln.domain.model.Currency
import ch.walica.exchange_rate_pln.domain.model.toCurrencyList
import ch.walica.exchange_rate_pln.presentation.add_currency_screen.AddCurrencyViewModel
import ch.walica.exchange_rate_pln.presentation.add_currency_screen.ListOperation
import ch.walica.exchange_rate_pln.presentation.ui.theme.color5

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddCurrencyScreen(
    navController: NavController,
    addCurrencyViewModel: AddCurrencyViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as? Activity
    var showMenu by remember {
        mutableStateOf(false)
    }
    val listOfCurrency = toCurrencyList()
    val state = addCurrencyViewModel.state


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(id = R.string.title_add_currency_1) + " ")
                            withStyle(style = SpanStyle(color = color5)) {
                                append(stringResource(id = R.string.title_add_currency_2))
                            }
                        }.toUpperCase(),
                        style = MaterialTheme.typography.h1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "arrow back")
                    }
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            if (listOfCurrency.isNotEmpty()) {
                val currencyMutableList = mutableListOf<String>()
                listOfCurrency.forEach { currency ->
                    currencyMutableList.add(currency.currency)
                }

                ExposedDropdownMenuBox(
                    expanded = state.isExpanded,
                    onExpandedChange = { addCurrencyViewModel.onAction(ListOperation.Expanded(!state.isExpanded))}) {
                    TextField(
                        value = state.selectedCurrency,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text(text = stringResource(id = R.string.select_currency))
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.isExpanded)
                        }
                    )
                    ExposedDropdownMenu(
                        expanded = state.isExpanded,
                        onDismissRequest = { addCurrencyViewModel.onAction(ListOperation.Expanded(false))  }) {
                        currencyMutableList.forEachIndexed { index, currency ->
                            DropdownMenuItem(onClick = {
                                addCurrencyViewModel.onAction(
                                    ListOperation.SelectedCurrency(
                                        listOfCurrency[index].currency
                                    )
                                )
                                addCurrencyViewModel.onAction(ListOperation.SelectedCode(listOfCurrency[index].code))
                                addCurrencyViewModel.onAction(ListOperation.Expanded(false))
                            }) {
                                Text(text = currency)
                            }
                        }
                    }
                }
                Button(onClick = {
                    addCurrencyViewModel.onAction(
                        ListOperation.AddCurrencyCode(
                            Currency(state.selectedCurrency, state.selectedCode)
                        )
                    )
                    navController.popBackStack()
                }) {
                    Text(text = stringResource(id = R.string.title_add_currency_1).uppercase())
                }
            }
        }
    }


}